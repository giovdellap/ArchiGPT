from flask import Flask
from flask_cors import CORS
import os
from config import ApplicationConfig
from routes.metric_bp import metric_bp


app = Flask(__name__)
CORS(app)

app.config['basedir'] = os.path.abspath(os.path.dirname(__file__))
app.config.from_object(ApplicationConfig)

app.register_blueprint(metric_bp, url_prefix='/metrics')

@app.route("/", methods=['GET'])
def home():
    return "Metric Handler Server is UP"

if __name__ == "__main__":
     app.run(host='0.0.0.0',port=5003 ,debug=True)
