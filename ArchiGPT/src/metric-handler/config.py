from dotenv import load_dotenv
import urllib.parse
from pymongo import MongoClient

load_dotenv()


class ApplicationConfig:
    BACKEND_HANDLER = "http://backend:5001"
