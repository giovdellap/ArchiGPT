from flask import Blueprint

from controllers.MetricController import generateProjects


metric_bp = Blueprint('metric_bp', __name__)

metric_bp.route('/generateProjects', methods=['POST'])(generateProjects)
