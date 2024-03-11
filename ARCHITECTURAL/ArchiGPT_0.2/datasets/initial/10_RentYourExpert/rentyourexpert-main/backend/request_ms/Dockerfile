FROM python:3.10-alpine
WORKDIR /request_ms
COPY requirements.txt /request_ms
RUN pip3 install -r requirements.txt
copy request_ms.py /request_ms

ENV FLASK_APP request_ms.py
ENV FLASK_ENV development
ENV FLASK_RUN_PORT 5004
ENV FLASK_RUN_HOST 0.0.0.0

EXPOSE 5004


CMD ["flask", "run"]