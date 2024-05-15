from flask import Blueprint

from controllers.GenerationController import generateDocumentB, generateEndpoints


generation_bp = Blueprint('generation_bp', __name__)

generation_bp.route('/generateDocumentB', methods=['POST'])(generateDocumentB)
generation_bp.route('/generateEndpoints', methods=['POST'])(generateEndpoints)

