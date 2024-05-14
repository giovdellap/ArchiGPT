from flask import Blueprint

from controllers.ProjectController import createProject, deleteProject, generateDocumentB, getAllProject


project_bp = Blueprint('project_bp', __name__)

project_bp.route('/', methods=['GET'])(getAllProject)
project_bp.route('/', methods=['POST'])(createProject)
project_bp.route('/', methods=['DELETE'])(deleteProject)

project_bp.route('/generateDocumentB', methods=['POST'])(generateDocumentB)

