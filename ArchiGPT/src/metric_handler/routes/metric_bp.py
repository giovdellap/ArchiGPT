from flask import Blueprint

from controllers.MetricController import generateProject, generateAllProjects


metric_bp = Blueprint('metric_bp', __name__)

metric_bp.route('/generateProject', methods=['POST'])(generateProject)
metric_bp.route('/generateAllProjects', methods=['POST'])(generateAllProjects)
