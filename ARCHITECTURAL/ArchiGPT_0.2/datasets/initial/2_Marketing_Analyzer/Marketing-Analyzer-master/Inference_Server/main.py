from distutils.command.config import config
from Dao import databaseCluster
import json
from Manager import Manager
from Server import Server
import os
import pandas as pd
from dotenv import load_dotenv

def main():
    load_dotenv(os.path.dirname(os.path.abspath(__file__)).split("Inference_Server")[0] + "Docker/.env")
    DatabaseConfig = {
        'uri' : os.getenv('database_uri_infer'),
        'database_name' : os.getenv('main_database'),
        'dataset_collection' : os.getenv('collection_dataset'),
        'confing_collection' : os.getenv('collection_dataset_configs'),
    }
    myserver = Server(DatabaseConfig)
    myserver.run()

if __name__ == '__main__':
    main()