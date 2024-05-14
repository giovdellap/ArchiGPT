from flask import Blueprint

from controllers.InternalController import interrogate


internal_bp = Blueprint('internal_bp', __name__)

internal_bp.route('/', methods=['POST'])(interrogate)
