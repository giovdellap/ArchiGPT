from flask import Blueprint, render_template, redirect, url_for,request,flash
from werkzeug.security import generate_password_hash, check_password_hash
from .models import Admin
from . import db
from flask_login import login_user, login_required, logout_user


auth = Blueprint('auth', __name__)

@auth.route('/login')
def login():
    return render_template('login.html')

@auth.route('/login', methods=['POST'])
def login_post():
    # login code goes here
    email = request.form.get('email')
    password = request.form.get('password')
    remember = True if request.form.get('remember') else False

    admin = Admin.query.filter_by(email=email).first()

    # check if the admin actually exists
    # take the admin-supplied password, hash it, and compare it to the hashed password in the database
    if not admin or not check_password_hash(admin.password, password):
        flash('Please check your login details and try again.')
        return redirect(url_for('auth.login')) # if the admin doesn't exist or password is wrong, reload the page

    login_user(admin, remember=remember)

    # if the above check passes, then we know the user has the right credentials
    return redirect(url_for('main.profile'))

@auth.route('/signup')
def signup():
    return render_template('signup.html')


@auth.route('/signup', methods=['POST'])
def signup_post():
    # code to validate and add user to database goes here
    email = request.form.get('email')
    name = request.form.get('name')
    password = request.form.get('password')

    admin = Admin.query.filter_by(email=email).first() # if this returns a admin, then the email already exists in database

    if admin: # if a admin is found, we want to redirect back to signup page so admin can try again
        flash('Email address already exists')
        return redirect(url_for('auth.signup'))

    # create a new admin with the form data. Hash the password so the plaintext version isn't saved.
    new_admin = Admin(email=email, name=name, password=generate_password_hash(password, method='sha256'))

    # add the new admin to the database
    db.session.add(new_admin)
    db.session.commit()

    return redirect(url_for('auth.login'))

@auth.route('/logout')
@login_required
def logout():
    logout_user()
    return redirect(url_for('main.index'))
