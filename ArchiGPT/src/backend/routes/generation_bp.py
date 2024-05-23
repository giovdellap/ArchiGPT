from flask import Blueprint

from controllers.ContainerController import generateContainer, getContainer
from controllers.GenerationController import generateSystem


generation_bp = Blueprint('generation_bp', __name__)

generation_bp.route('/generateSystem', methods=['POST'])(generateSystem)
generation_bp.route('/generateContainer', methods=['POST'])(generateContainer)
generation_bp.route('/getContainer', methods=['GET'])(getContainer)
