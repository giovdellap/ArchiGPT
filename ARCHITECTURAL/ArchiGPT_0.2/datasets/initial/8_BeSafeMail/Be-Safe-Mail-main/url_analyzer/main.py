import os
import requests
import urllib
import base64

from fastapi import FastAPI
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from typing import List
from pymongo import MongoClient
import redis

# from analyzer import ExternalAnalyzer

CONFIG = {
    "VirusTotal": "",
    "MetaScan": ""
}

# class to create analyzer
class ExternalAnalyzer:
    def __init__(self, name):
        
        self.name = name
        self.test_mode = False
        try:
            self.apikey = CONFIG[name]
        except KeyError as e:
            self.test_mode = True
        
        if name == "VirusTotal":
            self.base_url = "https://www.virustotal.com/api/v3/"
            def __get_url_reputation_vt(self,url_to_analyze):
                if self.test_mode:
                    return {"score": 0.5}
                url = f"https://www.virustotal.com/api/v3/urls/{url_to_analyze}"
                headers = {
                    'x-apikey': self.api_key,
                }
                response = requests.get(
                    url=url,
                    headers=headers
                )
                
                if response.status_code == 200:
                    result = response.json()
                    total = sum(result["data"]["attributes"]["last_analysis_stats"].values())
                    positives = result["data"]["attributes"]["last_analysis_stats"]["malicious"] + result["data"]["attributes"]["last_analysis_stats"]["suspicious"]
                    return {"score": positives/total}
                else:
                    print(response)
                    return None
  
            self.internal_get_url_reputation = __get_url_reputation_vt
        
        elif name == "MetaScan":
            self.base_url = "https://api.metadefender.com/v4/"
            def __get_url_reputation_ms(self, observable_url):
                if self.test_mode:
                    return {"score": 0.5}
                url = self.base_url + "url/" + urllib.parse.quote(observable_url)
                header = {
                    "apikey": self.apikey
                }
                r = requests.get(url=url, headers=header)
                if r.status_code != 200:
                    return None
                else:
                    res = r.json()
                    sources = res["lookup_results"]["sources"]
                    myres = {
                        "total": len(sources),
                        "detected": 0,
                        "positives": []
                    }
                    for source in sources:
                        if source["status"] == 1:
                            myres["detected"] += 1
                            myres["positives"].append(source["provider"])
                    return {"score":myres["positives"]/myres["total"]}

            self.internal_get_url_reputation = __get_url_reputation_ms
        else:
            def error_function(self, observable_url):
                return {"error": "function not defined"}
            self.internal_get_url_reputation = error_function

class AnalysisBody(BaseModel):
    urls: List[str] 


app = FastAPI()

# mongo things
client = MongoClient(os.getenv("MONGO_URI"))
db = client["mydatabase"]
collection = db["mycollection"]

# redis things
red = redis.Redis(
    host=os.getenv("REDIS_HOST"),
    port=os.getenv("REDIS_PORT"),
    db=0
)

def get_prefix_from_url(url: str) -> str:
    # Parse the URL using the urlparse() function
    parsed_url = urllib.parse.urlparse(url)
    
    # Extract the protocol and domain from the parsed URL
    prefix = f"{parsed_url.scheme}://{parsed_url.netloc}"
    
    # Return the prefix
    return prefix

def encode_base64(string):
    # Encode the string as bytes using UTF-8 encoding
    string_bytes = string.encode("utf-8")
    
    # Encode the bytes as base64 using the b64encode() function
    base64_bytes = base64.b64encode(string_bytes)
    
    # Decode the base64-encoded bytes to a string using UTF-8 encoding
    base64_string = base64_bytes.decode("utf-8")
    
    # Return the base64-encoded string
    return base64_string

@app.get("/")
def index():
    return {
        "hello": "world"
        }

@app.post("/url/")
def analyze_url(url_to_analyze: AnalysisBody):
    metascan = ExternalAnalyzer("MetaScan")
    virustotal = ExternalAnalyzer("VirusTotal")

    response = {}

    for url in url_to_analyze.urls:

        base64_str = encode_base64(get_prefix_from_url(url))
        result = red.get(base64_str)
        if result:
            response[url] = float(result)
            print("### Cache URL" + url + str(float(result)))
        else:
            print("### Not CACHED URL" + url)
            res_metascan = metascan.get_reputation_analyzer(url)
            res_virustotal = virustotal.get_reputation_analyzer(url)
            
            print(res_metascan)
            if res_metascan==None:
                print("### MS error")
                return JSONResponse(content=res_metascan, status_code=500)
            if res_virustotal==None:
                print("### VT error")
                # return JSONResponse(content=res_virustotal, status_code=500)
            # score = (res_metascan["detected"] + res_virustotal["detected"])/(res_virustotal["total"] + res_metascan["total"])
            
            score = (res_metascan["score"] + res_virustotal["score"])/2
            response[url] = score

            # store result on redis for caching
            red.set(base64_str, score)

            aggregate_bulk = {
                "base64_str": base64_str,
                "VirusTotal" : res_virustotal,
                "Metascan": res_metascan,
            }

            # store full analysis into mongodb
            collection.insert_one(aggregate_bulk)
    
    return response

