FROM python:3.10-alpine
WORKDIR /management_ms
COPY requirements.txt /management_ms
RUN pip3 install -r requirements.txt
copy management_ms.py /management_ms

ENV FLASK_APP management_ms.py
ENV FLASK_ENV development
ENV FLASK_RUN_PORT 5002
ENV FLASK_RUN_HOST 0.0.0.0

EXPOSE 5002


CMD ["flask", "run"]