from flask import Blueprint

from controllers.AssistantController import create_assistant, get_list_assistants

assistant_bp = Blueprint('assistant_bp', __name__)

assistant_bp.route('/', methods=['GET'])(get_list_assistants)
assistant_bp.route('/', methods=['POST'])(create_assistant)
#assistant_bp.route('/<int:assistant_id>', methods=['GET'])(get_assistant)
#assistant_bp.route('/<int:assistant_id>/edit', methods=['PUT'])(update_assistant)
#assistant_bp.route('/<int:assistant_id>', methods=['DELETE'])(delete_assistant)
