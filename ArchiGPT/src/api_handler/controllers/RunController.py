from flask import request, jsonify

from api_reference.threads.runsAPI import runCreationHandler


def create_run(thread_id):
    try:
        if not request.json['assistant_id'] :
            return jsonify({"message": "assistant_id is required in the json body"}), 400
        assistant_id = request.json['assistant_id']

        run = runCreationHandler(thread_id,assistant_id)

        return jsonify({"run_id": run.id}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500