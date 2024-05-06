from pymongo import MongoClient

class DBHandler:

    mongo_uri = "mongodb://mongodb:27017"

    def create_database(self, name):
        try:
            self.mongodb_client = MongoClient(self.mongo_uri)
            database = self.mongodb_client[name]
            #db_list = self.mongodb_client.list_database_names()
            #if name in db_list:
            #    return 'DB created'
            #else:
            #    return 'DB creation failed'
            return 'ao'
        except Exception as e:
            print("Exception: %s", e)
            return e

    def startup_db_client(self, database):
        self.mongodb_client = MongoClient(self.mongo_uri)
        database = self.mongodb_client[database]
        print("Connected to the MongoDB database!")

    def shutdown_db_client(self):
        self.mongodb_client.close()
        
    def testDB(self):
        try:
            self.mongodb_client = MongoClient(self.mongo_uri)
            db_list = self.mongodb_client.list_database_names()
            #if name in db_list:
            #    return 'DB created'
            #else:
            #    return 'DB creation failed'
            return len(db_list)
        except Exception as e:
            print("Exception: %s", e)
            return e