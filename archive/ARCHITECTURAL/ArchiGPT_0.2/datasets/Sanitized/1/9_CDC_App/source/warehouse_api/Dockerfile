FROM python:3.10-alpine

WORKDIR /code
COPY requirements.txt /code
RUN pip3 install -r requirements.txt

COPY . .

ENV FLASK_APP warehouse_api.py
ENV FLASK_ENV development
ENV FLASK_RUN_PORT 8000
ENV FLASK_RUN_HOST 0.0.0.0

EXPOSE 8000

CMD ["flask", "run"]