from flask import Blueprint

from controllers.ThreadController import create_thread
from controllers.MessageController import create_message, get_last_message
from controllers.RunController import create_run

thread_bp = Blueprint('thread_bp', __name__)


#Thread API

#thread_bp.route('/', methods=['GET'])(get_all_threads)
thread_bp.route('/', methods=['POST'])(create_thread)
#thread_bp.route('/<int:thread_id>', methods=['GET'])(get_thread)
#thread_bp.route('/<int:thread_id>/edit', methods=['PUT'])(update_thread)
#thread_bp.route('/<int:thread_id>', methods=['DELETE'])(delete_thread)


#Message API

thread_bp.route('<string:thread_id>/message', methods=['GET'])(get_last_message)
thread_bp.route('<string:thread_id>/message', methods=['POST'])(create_message)
#thread_bp.route('/<int:message_id>', methods=['GET'])(get_message)
#thread_bp.route('/<int:message_id>/edit', methods=['PUT'])(update_message)
#thread_bp.route('/<int:message_id>', methods=['DELETE'])(delete_message)


#Run API

#thread_bp.route('/', methods=['GET'])(get_all_messages)
thread_bp.route('<string:thread_id>/run', methods=['POST'])(create_run)
#thread_bp.route('/<int:message_id>', methods=['GET'])(get_run)
#thread_bp.route('/<int:message_id>/edit', methods=['PUT'])(update_run)
#thread_bp.route('/<int:message_id>', methods=['DELETE'])(delete_run)