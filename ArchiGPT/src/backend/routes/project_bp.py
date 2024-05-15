from flask import Blueprint

from controllers.ProjectController import createProject, createTestProject, deleteProject, generateDocumentB, generateEndpoints, getAllProject, getStatus


project_bp = Blueprint('project_bp', __name__)

project_bp.route('/', methods=['GET'])(getAllProject)
project_bp.route('/', methods=['POST'])(createProject)
project_bp.route('/', methods=['DELETE'])(deleteProject)

project_bp.route('/status', methods=['GET'])(getStatus)
project_bp.route('/test', methods=['POST'])(createTestProject)

project_bp.route('/generateDocumentB', methods=['POST'])(generateDocumentB)
project_bp.route('/generateEndpoints', methods=['POST'])(generateEndpoints)

