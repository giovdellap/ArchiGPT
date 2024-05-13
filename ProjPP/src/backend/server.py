from flask import Flask
from flask_cors import CORS
import os
from config import ApplicationConfig
from routes.database_bp import database_bp
from routes.project_bp import project_bp


app = Flask(__name__)
CORS(app, supports_credentials=True)

app.config['basedir'] = os.path.abspath(os.path.dirname(__file__))
app.config.from_object(ApplicationConfig)

app.register_blueprint(database_bp, url_prefix='/db')
app.register_blueprint(project_bp, url_prefix='/project')


@app.route("/", methods=['GET'])
def home():
    return "Automation Server is UP"


if __name__ == "__main__":
     app.run(host='0.0.0.0',port=5001 ,debug=True)
