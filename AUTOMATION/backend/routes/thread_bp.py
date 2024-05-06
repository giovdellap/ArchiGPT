from flask import Blueprint

from controllers.ThreadController import create_thread
from controllers.MessageController import create_message

thread_bp = Blueprint('thread_bp', __name__)

#thread_bp.route('/', methods=['GET'])(get_all_threads)
thread_bp.route('/', methods=['POST'])(create_thread)
#thread_bp.route('/<int:thread_id>', methods=['GET'])(get_thread)
#thread_bp.route('/<int:thread_id>/edit', methods=['PUT'])(update_thread)
#thread_bp.route('/<int:thread_id>', methods=['DELETE'])(delete_thread)


#message_bp.route('/', methods=['GET'])(get_all_messages)
thread_bp.route('<string:thread_id>/message', methods=['POST'])(create_message)
#message_bp.route('/<int:message_id>', methods=['GET'])(get_message)
#message_bp.route('/<int:message_id>/edit', methods=['PUT'])(update_message)
#message_bp.route('/<int:message_id>', methods=['DELETE'])(delete_message)