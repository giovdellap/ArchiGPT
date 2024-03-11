import pymongo
import json

class databaseCluster:
    def __init__(self, uri):
        self.client = pymongo.MongoClient(uri)
        try:
            self.client.server_info()
        except Exception:
            print("Unable to connect to the server.")
        

    def init(self, databaseName, collectionName):

        try:
            self.database =  self.client[databaseName]
            self.coll = self.database[collectionName]
        except Exception as err:
            raise Exception(err)

    def getAllDocs(self):
        try:
            return self.coll.find({})
        except Exception as err:
            raise Exception(err)