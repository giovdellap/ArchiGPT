from dotenv import load_dotenv
import os
from openai import OpenAI

load_dotenv()


class ApplicationConfig:
    MONGO_USERNAME = "user"
    MONGO_PASSWORD = "pass"
    API_HANDLER = "http://api-handler:10001"
