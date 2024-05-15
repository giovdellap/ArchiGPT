from flask import Blueprint

from controllers.ProjectController import createProject, deleteProject, getAllProject, getStatus, getSystem


project_bp = Blueprint('project_bp', __name__)

project_bp.route('/', methods=['GET'])(getAllProject)
project_bp.route('/', methods=['POST'])(createProject)
project_bp.route('/', methods=['DELETE'])(deleteProject)

project_bp.route('/status', methods=['GET'])(getStatus)
project_bp.route('/system', methods=['GET'])(getSystem)

