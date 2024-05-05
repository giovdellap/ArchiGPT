from dotenv import load_dotenv
import os

load_dotenv()


class ApplicationConfig:
    ASSISTANT_API = os.environ["ASSISTANT_API"]
