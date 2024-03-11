FROM python:3.10-alpine
WORKDIR /worker_login_ms
COPY requirements.txt /worker_login_ms
RUN pip3 install -r requirements.txt
copy worker_login_ms.py /worker_login_ms


ENV FLASK_APP worker_login_ms.py
ENV FLASK_ENV development
ENV FLASK_RUN_PORT 5001
ENV FLASK_RUN_HOST 0.0.0.0

EXPOSE 5001


CMD ["flask", "run"]