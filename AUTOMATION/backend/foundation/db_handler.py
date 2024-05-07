from pymongo import MongoClient
import urllib.parse

class DBHandler:
    db_username = "user"
    db_password = "pass"
    uri_username = urllib.parse.quote_plus(db_username)
    uri_password = urllib.parse.quote_plus(db_password)
    mongo_uri = 'mongodb://%s:%s@mongodb:27017' % (uri_username, uri_password)
    
    project_db = 'projects'


    def startup_db_client(self, database):
        self.mongodb_client = MongoClient(self.mongo_uri)
        database = self.mongodb_client[database]
        return database

    def shutdown_db_client(self):
        self.mongodb_client.close()
        
    def create_database(self, db_name, collection_name):
        try:
            self.mongodb_client = MongoClient(self.mongo_uri)
            database = self.mongodb_client[db_name]
            collection = database.create_collection(collection_name)
            db_list = self.mongodb_client.list_database_names()
            if db_name in db_list:
                return 'DB created'
            else:
                return 'DB creation failed'
        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def create_collection(self, collection_name):
        try:
            database = self.startup_db_client(self.project_db)
            collection = database.create_collection(collection_name)
            self.shutdown_db_client()
        except Exception as e:
            print("Exception: %s", e)
            return e

        
    def testDB(self):
        try:
            self.mongodb_client = MongoClient(self.mongo_uri)
            db_list = self.mongodb_client.list_database_names()
            return len(db_list)
        except Exception as e:
            print("Exception: %s", e)
            return e