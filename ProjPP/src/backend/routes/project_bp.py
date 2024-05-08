from flask import Blueprint

from controllers.ProjectController import createProject


project_bp = Blueprint('project_bp', __name__)

project_bp.route('/create_project', methods=['POST'])(createProject)

