from flask import Flask
from datetime import date
import yfinance as yf
from fbprophet import Prophet
from flask import jsonify
from flask_cors import CORS
from flask import send_file
app = Flask(__name__)
CORS(app)
START = "2015-01-01"
TODAY = date.today().strftime("%Y-%m-%d")

@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

@app.route("/getStock/<stock>")
def get_stock(stock):
    #get base stock data
    data = yf.download(stock, START, TODAY)
    data.reset_index(inplace=True)
   
    #predict future stock data
    df_train = data[['Date','Close']]
    df_train = df_train.rename(columns={"Date": "ds", "Close": "y"})
    m = Prophet()
    m.fit(df_train)
    future = m.make_future_dataframe(periods= 2*365)
    forecast = m.predict(future)
    
    #send both data back
    all_data = {"dati" : data.values.tolist() , "forecast" : forecast.values.tolist()}
    m.plot_components(forecast).savefig('./grafici.png')
    return all_data

@app.route("/prendiGrafici")
def getGrafici():
    return send_file("./grafici.png")























"""
@app.route("/")
def predict_stocks(stocks):
    df_train = data[['Date','Close']]
    df_train = df_train.rename(columns={"Date": "ds", "Close": "y"})

    m = Prophet()
    m.fit(df_train)
    future = m.make_future_dataframe(periods=period)
    forecast = m.predict(future)
"""



