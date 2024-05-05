from flask import Blueprint

from controllers.ThreadController import create_thread

thread_bp = Blueprint('thread_bp', __name__)

#thread_bp.route('/', methods=['GET'])(get_all_threads)
thread_bp.route('/create', methods=['POST'])(create_thread)
#thread_bp.route('/<int:thread_id>', methods=['GET'])(get_thread)
#thread_bp.route('/<int:thread_id>/edit', methods=['PUT'])(update_thread)
#thread_bp.route('/<int:thread_id>', methods=['DELETE'])(delete_thread)
