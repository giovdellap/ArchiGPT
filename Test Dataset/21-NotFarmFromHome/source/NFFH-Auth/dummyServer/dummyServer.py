from flask import Flask, request, jsonify, make_response, request, render_template, session, flash
from datetime import datetime, timedelta
from functools import wraps

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


@app.route('/login', methods=['post'])
def getUser():
    
    mail = request.args.get(h_mail)
    
    print(request.headers)
    
    if (mail == "test@gmail.com"):
        return jsonify({h_succ:True, h_pass: "Difficult password"})

    else:
        return jsonify({h_succ:False, h_pass: "NOT FOUND"})

@app.route('/signup', methods=['post'])
def signup():
    
    diReq = {}
 
    diReq["mail"] = request.args.get(h_mail)
    diReq["passw"] = request.args.get(h_pass)
    diReq["name"] = request.args.get(h_name)
    diReq["username"] = request.args.get(h_username)
    diReq["image"] = request.args.get(h_image)
    diReq["area"] = request.args.get(h_area)
    diReq["address"] = request.args.get(h_address)
    
    print(diReq)
    
    return jsonify({h_succ:True})

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=9703, debug=True)