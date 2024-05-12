from flask import Flask, request, jsonify, make_response, request, render_template, session, flash
import jwt
import requests

app = Flask(__name__)

secret = "My_very_important_secret"

h_mail = "email"
h_pass = "password"
h_role = "role"
h_username = "username"
h_image = "image"
h_area= "area"
h_token = "token"
h_name = "name"
h_address = "address"
h_succ = "success"
h_errStringLogin = "error"
h_id = "id"

dbClientAddress = "http://client-be:9702"
dbFarmerAddress = "http://farmer-be:9703"

s_invalidToke = "FAIL"

@app.route('/<role>/login', methods=['POST'])
def login(role):
    
    # Dictionary of the json in the body of the request
    requestBody = request.get_json()
    
    # variable to store
    mail = requestBody['email']
    passw = requestBody['password']
    
    # Header of the token UNCRYPTED
    headerToken = {
        "mail" : mail, 
        "algo":"HS256"
    }
    
    # Request to do at the BE
    RequestToBEBody = {
        "email" : mail
    }
    
    # Data that will be encrypted in the token
    toEncryptData = { 
        h_mail:mail, 
        h_pass:passw, 
        h_role : role
    }
    
    
    # Depending on role i will do requests on differt servers
    if role == "farmer":
        dict_response = requests.post(url=dbFarmerAddress+"/farmer/login", json=RequestToBEBody).json() 
    elif role == "client":
        dict_response = requests.post(url=dbClientAddress+"/client/login", json=RequestToBEBody).json()
    elif role == "admin":
        dict_response = {'password': 'mario', 'success': True, h_id : 'admin', "username":"admin"}
    # if the db response = no succ -> no mail in the db
    if( dict_response["success"] == False ):
        suc = False
        token = s_invalidToke
        errString = "NO_MAIL"
        
    # if invalid password and succ : True
    elif(dict_response["password"] != passw ):
        token = s_invalidToke
        suc = False
        errString = "NO_PASS"
    # There is the user in db and the pass is valid
    else:
        # Here lies 5h of our time just to remember that you need to add .decode in this sting
        token = jwt.encode(payload=toEncryptData, key=secret, algorithm="HS256",headers=headerToken).decode('utf-8')
        suc = True
        errString = "GOOD"

    # Json of the responsee to the FE
    final_Response = {
        "success" : suc , 
        "token": token, 
        "error":errString,
        "id" : dict_response["id"],
        "username": dict_response["username"]
    }
    
    return final_Response


    

@app.route('/<role>/signup', methods=['POST'])
def register(role):
    
    # Get all info per specification
    requestBody = request.get_json()
    
    # all paramaters in the json of the post
    mail = requestBody[h_mail]
    passw = requestBody[h_pass]
    username = requestBody[h_username]
    if(role == "farmer"):
        image = requestBody[h_image]
        area = requestBody[h_area]
        address = requestBody[h_address]
    

    #head of toke, uncrypted
    headerToken = {
        h_mail : mail, 
        h_role: role, 
        "algo":"HS256" 
    }
    
    # Body for the post request to the db
    
    if (role == "farmer"):
    
        postBody = {
            "username" : username,
            "email" : mail,
            "password" : passw,
            "image" : image,
            "area" : area,
            "address" : address
        }
    
        # Response
        dictResponse = requests.post(url=dbFarmerAddress+"/farmer", json=postBody)

        # Body of the response
        ResponseBody = dictResponse.json()
    
    elif (role == "client"):
        postBody = {
            "username" : username,
            "email" : mail,
            "password" : passw
        }
    
        # Response
        dictResponse = requests.post(url=dbClientAddress+"/client", json=postBody)
        # dictionary of the body of the response
        ResponseBody = dictResponse.json()
    
    #debug
    #print("User succesfully created in the db")
    
    
    #Succesfuly created user in db
    if(ResponseBody['success'] == True):
        
        # Info to be encrypeted in the token
        dictToEncode = {
            h_mail : mail,
            h_pass : passw,
            h_role : role
        }
        
        # token generated ed encoded
        token = jwt.encode(payload=dictToEncode, key=secret, algorithm="HS256", headers=headerToken).decode('utf-8')
        suc = True
    
    
    # no new user
    else:
        token = s_invalidToke
        suc = False
    
    # Build the json of the response
    finalResponse = {
        h_succ : suc,
        h_token: token,
        h_id: ResponseBody[h_id]
    }
    
    return finalResponse
    
# Post for verification of the token of the user    
@app.route('/verifyToken', methods=['POST'])
def verifyToken():
    
    #dictionary of the json of the response
    requestBody = request.get_json()
    
    # token from dict
    token_received = requestBody[h_token]
    
    try:
        dicHeaders = jwt.get_unverified_header(token_received)
        
        print(dicHeaders)
        
        verify = jwt.decode(token_received, secret, algorithms=dicHeaders["algo"])

        return {
            h_succ:True, 
            h_mail: verify.get("email"),
            h_role: verify.get(h_role)}
        
        
    except jwt.exceptions.DecodeError:
        
        return {h_succ:False}
    
    return {h_succ:False}
    
if __name__ == "__main__":
    app.run(debug=True, port=9701, host='0.0.0.0')
    