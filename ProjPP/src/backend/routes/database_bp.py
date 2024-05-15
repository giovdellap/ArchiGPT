from flask import Blueprint

from controllers.DatabaseController import create_database, testDB


database_bp = Blueprint('database_bp', __name__)

database_bp.route('/create_database', methods=['POST'])(create_database)


database_bp.route('/test', methods=['GET'])(testDB)
