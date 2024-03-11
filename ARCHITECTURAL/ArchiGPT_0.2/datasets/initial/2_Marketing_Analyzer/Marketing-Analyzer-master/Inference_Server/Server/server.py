import imp
from flask import Flask, request, jsonify
from flask_restful import Resource, Api
from flask_cors import CORS

from Manager import Manager
from .Routers import InferenceRouter
import json
import os

config_path = os.getcwd().split("Server")[0] + 'config.json'

class Server:
    def __init__(self, databaseConfig):
        self.app = Flask(__name__)
        self.api = Api(self.app)
        self.manager = Manager(databaseConfig)
        self.api.add_resource(InferenceRouter, '/infer', resource_class_kwargs = {"manager" : self.manager})
        CORS(self.app)

    def run(self):
        self.app.run(host='0.0.0.0', port=8080)