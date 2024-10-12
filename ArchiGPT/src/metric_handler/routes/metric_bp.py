from flask import Blueprint

from controllers.MetricController import generateProject, generateAllProjects, getProjectJson


metric_bp = Blueprint('metric_bp', __name__)

metric_bp.route('/generateProject', methods=['POST'])(generateProject)
metric_bp.route('/generateAllProjects', methods=['POST'])(generateAllProjects)
metric_bp.route('/getProjectJson', methods=['POST'])(getProjectJson)
