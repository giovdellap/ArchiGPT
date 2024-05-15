from dotenv import load_dotenv
import urllib.parse
from pymongo import MongoClient

load_dotenv()


class ApplicationConfig:
    MONGO_USERNAME = "user"
    MONGO_PASSWORD = "pass"
    API_HANDLER = "http://api-handler:10001"

    #Mongo Configuration
    uri_username = urllib.parse.quote_plus(MONGO_USERNAME)
    uri_password = urllib.parse.quote_plus(MONGO_PASSWORD)
    mongo_uri = 'mongodb://%s:%s@mongodb:27017' % (uri_username, uri_password)
    MONGO_CLIENT = MongoClient(mongo_uri)
