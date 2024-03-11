FROM python:3.10-alpine
WORKDIR /qea_ms
COPY requirements.txt /qea_ms
RUN pip3 install -r requirements.txt
copy qea_ms.py /qea_ms

ENV FLASK_APP qea_ms.py
ENV FLASK_ENV development
ENV FLASK_RUN_PORT 5005
ENV FLASK_RUN_HOST 0.0.0.0

EXPOSE 5005


CMD ["flask", "run"]