from flask import Blueprint

from controllers.GenerationController import generateSystem


generation_bp = Blueprint('generation_bp', __name__)

generation_bp.route('/generateSystem', methods=['POST'])(generateSystem)
