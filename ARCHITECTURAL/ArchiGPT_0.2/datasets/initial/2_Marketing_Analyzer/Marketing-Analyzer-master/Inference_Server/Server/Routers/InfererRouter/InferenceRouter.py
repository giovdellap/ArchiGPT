from flask_restful import Resource
from flask import Flask, request, jsonify

class InferenceRouter(Resource):
    def __init__(self, **kwargs):
        super().__init__()
        for key, value in kwargs.items():
            if (key == "manager"):
                self.manager = value

    def get(self):
        return {"Message": "Welcome to Inferer API, use a post request with your product sample to infer"}

    def post(self):
        reqbody = request.json
        result = self.manager.InferSample(reqbody['sample'])
        return jsonify(result)