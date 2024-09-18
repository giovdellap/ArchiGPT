import bson
from flask import current_app


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
        
    # UPDATE FUNCTIONS    
    
    def updateSystemStatus(self, projectname, ass_name, ass_status):
        try:
            collection = self.database[projectname]

            # Perform the update
            result = collection.update_one(
                filter = {"type": "status"},
                update = {"$set": {"data.system.$[element].status": ass_status}},
                array_filters = [{"element.name": ass_name}],
                upsert=True
            )

            print('DB HANDLER - UPDATE SYSTEM STATUS')

        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def updateContainerStatus(self, projectname, ass_name, ass_status, container):
        try:
            collection = self.database[projectname]

            # Perform the update
            field = "data.containers.$[element]." + ass_name
            result = collection.update_one(
                filter = {"type": "status"},
                update = {"$set": {field: ass_status}},
                array_filters = [{"element.name": container}],
                upsert=True
            )

            print('DB HANDLER - UPDATE CONTAINER STATUS')

        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def updateServiceStatus(self, projectname, ass_name, ass_status, container, service):
        try:
            collection = self.database[projectname]

            # Perform the update
            field = "data.containers.$[containerElement].services.$[serviceElement]." + ass_name
            result = collection.update_one(
                filter = {"type": "status"},
                update = {"$set": {field: ass_status}},
                array_filters = [{"containerElement.name": container},{"serviceElement.name": service, f"serviceElement.{ass_name}": {"$exists": True}}],
                upsert=True
            )

            print('DB HANDLER - UPDATE SERVICE STATUS')

        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def updateSystem(self, projectname, ass_name, ass_message):
        try:
            collection = self.database[projectname]
            result = collection.update_one(
                filter = {"type": "system"},
                update = {"$set": {"data.$[element].message": ass_message}},
                array_filters = [{"element.name": ass_name}],
                upsert=True
            )

            print('DB HANDLER - UPDATE SYSTEM')

        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def updateContainer(self, projectname, container, ass_name, ass_message):

        try:
            field = "data." + ass_name
            collection = self.database[projectname]
            result = collection.update_one(
                filter = {"$and":[{"type": "container"}, {"data.name": container}]},
                update = {"$set": {field: ass_message}},
                upsert=True
            )

            print('DB HANDLER - UPDATE CONTAINER')
            
        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def updateService(self, projectname, container, service, ass_name, ass_message):

        try:
            field = "data.services.$[element]." + ass_name
            collection = self.database[projectname]
            result = collection.update_one(
                filter = {"$and":[{"type": "container"}, {"data.name": container}]},
                update = {"$set": {field: ass_message}},
                array_filters = [{"element.name": service}],
                upsert=True
            )

            print('DB HANDLER - UPDATE SERVICE')
            
        except Exception as e:
            print("Exception: %s", e)
            return e
        
        
    # INSERTION FUNCTIONS
    
    def insertContainersinStatus(self, list, projectname):
        try:
            collection = self.database[projectname]
            # Perform the update
            for element in list:
                result = collection.update_one(
                    filter = {"type": "status"},
                    update = 
                        {"$addToSet": {"data.containers": element}},
                    
                    upsert=True
                )

            print('DB HANDLER - CONTAINERS INSERTED IN STATUS')

        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def insertServicesinStatus(self, list, projectname, container):
        try:
            collection = self.database[projectname]
            # Perform the update
            for element in list:
                result = collection.update_one(
                    filter = {"type": "status"},
                    update = 
                        {"$addToSet": {"data.containers.$[container]": element}},
                    
                    upsert=True
                )

            print('DB HANDLER - SERVICES INSERTED IN STATUS')

        except Exception as e:
            print("Exception: %s", e)
            return e    
    
    def insertContainersDocuments(self, documents, projectname):
        try:
            collection = self.database[projectname]
            result = collection.insert_many(documents)

            print('DB HANDLER - CONTAINER DOCUMENTS INSERTED')

        except Exception as e:
            print("Exception: %s", e)
            return e
        
    def updateContainerDocument(self, projectname, containername, field, message):
        try:
            collection = self.database[projectname]
            result = collection.update_one(
                filter = {"type": "container"},
                update = {"$set": {"data.$field": message}},
                array_filters = [{"element.name": containername}],
                upsert=True
            )

            print('DB HANDLER - CONTAINER DOCUMENTS UPDATED')

        except Exception as e:
            print("Exception: %s", e)
            return e
        
        
    ## GET FUNCTIONS
        
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
        
    def getContainer(self, collection, container):
        try:
            col = self.database[collection]
            system = col.find_one({"$and":[{"type": "container"}, {"data.name": container}]})
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