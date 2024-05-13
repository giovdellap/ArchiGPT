from dotenv import load_dotenv
import os
from openai import OpenAI

load_dotenv()


class ApplicationConfig:
    OPENAI_API_KEY = os.environ["OPENAI_API_KEY"]
    MONGO_USERNAME = "user"
    MONGO_PASSWORD = "pass"
    #OPENAI_API_KEY_G = "sk-proj-uoPd7wzdubIrsFdGwVDDT3BlbkFJRJIzt4ImRPIvvMhy9ShK"
    DEFAULT_PATH = "http://127.0.0.1:5001"

    CLIENT = OpenAI(api_key = OPENAI_API_KEY)
