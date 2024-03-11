FROM python:3.10-alpine
WORKDIR /review_ms
COPY requirements.txt /review_ms
RUN pip3 install -r requirements.txt
copy review_ms.py /review_ms

ENV FLASK_APP review_ms.py
ENV FLASK_ENV development
ENV FLASK_RUN_PORT 5006
ENV FLASK_RUN_HOST 0.0.0.0

EXPOSE 5006


CMD ["flask", "run"]