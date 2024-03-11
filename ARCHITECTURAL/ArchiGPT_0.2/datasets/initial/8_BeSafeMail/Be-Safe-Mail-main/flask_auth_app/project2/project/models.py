from . import db
from flask_login import UserMixin

class User(UserMixin,db.Model):
    id = db.Column(db.Integer, primary_key=True) # primary keys are required by SQLAlchemy
    email = db.Column(db.String(100), unique=True)
    password = db.Column(db.String(100))
    name = db.Column(db.String(1000))


#provo a creare classe per email
class RiskEmail(db.Model):
    id = db.Column(db.Integer, primary_key=True) # primary keys are required by SQLAlchemy
    recipient = db.Column(db.String(100))
    text = db.Column(db.String(100))
    sender = db.Column(db.String(1000))



class RiskEmail2(db.Model):
    id = db.Column(db.Integer, primary_key=True) # primary keys are required by SQLAlchemy
    recipient = db.Column(db.String(100))
    text = db.Column(db.String(100))
    sender = db.Column(db.String(1000))
    score = db.Column(db.Integer)
