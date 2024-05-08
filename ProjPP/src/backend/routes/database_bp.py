from flask import Blueprint

from controllers.DatabaseController import create_database, create_project, testDB


database_bp = Blueprint('database_bp', __name__)

database_bp.route('/create_database', methods=['POST'])(create_database)
database_bp.route('/create_project', methods=['POST'])(create_project)


database_bp.route('/test', methods=['GET'])(testDB)
