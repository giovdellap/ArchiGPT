from flask import Blueprint

from controllers.ProjectController import createProject, generateDocumentB


project_bp = Blueprint('project_bp', __name__)

project_bp.route('/createProject', methods=['POST'])(createProject)
project_bp.route('/generateDocumentB', methods=['POST'])(generateDocumentB)

