from flask import Blueprint

from controllers.AssistantController import create_assistant, get_list_assistants, update_assistant, delete_assistant

assistant_bp = Blueprint('assistant_bp', __name__)

assistant_bp.route('/', methods=['POST'])(create_assistant)
assistant_bp.route('/', methods=['GET'])(get_list_assistants)
#assistant_bp.route('/<string:assistant_id>', methods=['GET'])(get_assistant)
assistant_bp.route('/<string:assistant_id>', methods=['PUT'])(update_assistant)
assistant_bp.route('/<string:assistant_id>', methods=['DELETE'])(delete_assistant)
