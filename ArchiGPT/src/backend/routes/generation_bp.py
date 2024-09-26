from flask import Blueprint

from controllers.ServiceController import generateService, regenerateService, getService
from controllers.ContainerController import generateContainer, regenerateContainer, getContainer
from controllers.GenerationController import generateSystem, regenerateSystem


generation_bp = Blueprint('generation_bp', __name__)

generation_bp.route('/generateSystem', methods=['POST'])(generateSystem)
generation_bp.route('/generateContainer', methods=['POST'])(generateContainer)
generation_bp.route('/generateService', methods=['POST'])(generateService)
generation_bp.route('/getContainer', methods=['GET'])(getContainer)
#TODO generation_bp.route('/getService', methods=['GET'])(getService)

#TODO generation_bp.route('/regenerateSystem', methods=['PATCH'])(regenerateSystem)
generation_bp.route('/regenerateContainer', methods=['PATCH'])(regenerateContainer)
generation_bp.route('/regenerateService', methods=['PATCH'])(regenerateService)
