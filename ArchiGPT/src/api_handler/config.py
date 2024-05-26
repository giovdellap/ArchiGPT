from dotenv import load_dotenv
import os
from openai import OpenAI

load_dotenv()


class ApplicationConfig:
    OPENAI_API_KEY = os.environ["OPENAI_API_KEY"]
    MONGO_USERNAME = "user"
    MONGO_PASSWORD = "pass"
    DEFAULT_PATH = "http://127.0.0.1:10001"
    DEMO = False
    CLIENT = OpenAI(api_key = OPENAI_API_KEY)
