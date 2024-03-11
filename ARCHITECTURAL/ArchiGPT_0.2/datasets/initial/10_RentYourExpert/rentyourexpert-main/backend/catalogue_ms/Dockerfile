FROM python:3.10-alpine
WORKDIR /catalogue_ms
COPY requirements.txt /catalogue_ms
RUN pip3 install -r requirements.txt
copy catalogue_ms.py /catalogue_ms

ENV FLASK_APP catalogue_ms.py
ENV FLASK_ENV development
ENV FLASK_RUN_PORT 5000
ENV FLASK_RUN_HOST 0.0.0.0

EXPOSE 5000


CMD ["flask", "run"]