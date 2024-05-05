from dotenv import load_dotenv
import os

load_dotenv()


class ApplicationConfig:
    OPENAI_API_KEY = os.environ["OPENAI_API_KEY"]
