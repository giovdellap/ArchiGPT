#from dotenv import load_dotenv
import os
from openai import OpenAI

#load_dotenv()


class ApplicationConfig:
    #OPENAI_API_KEY = os.environ["OPENAI_API_KEY"]
    OPENAI_API_KEY_G = "sk-proj-uoPd7wzdubIrsFdGwVDDT3BlbkFJRJIzt4ImRPIvvMhy9ShK"
    OPENAI_API_KEY_E = ""
    CLIENT = OpenAI(api_key = OPENAI_API_KEY_G)
