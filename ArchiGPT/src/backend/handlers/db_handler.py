import bson
from flask import current_app
from pymongo import MongoClient


class DBHandler:
    project_dbName = 'projects'

    def create_database(self, db_name, collection_name):
        try:
            self.project_dbName = db_name
            database = current_app.config['MONGO_CLIENT'][db_name]
            database.create_collection(collection_name)
            db_list = self.mongodb_client.list_database_names()
            if db_name in db_list:
                return 'DB created'
            else:
                return 'DB creation failed'
        except Exception as e:
            print("Exception: %s", e)
            return e

    def database_setup(self):
        try:
            self.database = current_app.config['MONGO_CLIENT'][self.project_dbName]
        except Exception as e:
            print("Exception: %s", e)

    def shutdown_db_client(self):
        try:
            current_app.config['MONGO_CLIENT'].close()
        except Exception as e:
            print("Exception: %s", e)
            return e

    def getAllProjects(self):
        try:
            projects_db = current_app.config['MONGO_CLIENT'][self.project_dbName]
            collection_names = projects_db.list_collection_names()
            projects_info = []

            for collection_name in collection_names:
                collection = projects_db[collection_name]
                num_documents = collection.count_documents({})
                projects_info.append({
                    'name': collection_name,
                    'num_documents': num_documents
                })

            return projects_info

        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def create_collection(self, collection_name):
        try:
            self.database.create_collection(collection_name)
        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def delete_collection(self, collection_name):
        try:
            projects_db = current_app.config['MONGO_CLIENT'][self.project_dbName]
            projects_db.drop_collection(collection_name)

        except Exception as e:
            print("Exception: %s", e)
            return e

    def addDocument(self, collection, document):
        try:
            col = self.database[collection]
            col.insert_one(document)
        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def updateDocument(self, collection, document, key, value):
        try:
            col = self.database[collection]
            query_filter = {'type' : 'document'}
            update_operation = { '$set' : 
                { 'key' : 'value' }
            }
            col.update_one(query_filter, update_operation)
        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def getStatus(self, collection):
        try:
            col = self.database[collection]
            status = col.find_one({'type': 'status'})
            return bson.json_util.dumps(status)
        except Exception as e:
            print("Exception: %s", e)
            return e
    
    def getSystem(self, collection):
        try:
            col = self.database[collection]
            system = col.find_one({'type': 'system'})
            return bson.json_util.dumps(system)
        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def testDB(self):
        try: 
            db_list = current_app.config['MONGO_CLIENT'].list_database_names()
            return len(db_list)
        except Exception as e:
            print("Exception: %s", e)
            return e