FROM python:3.11.6-alpine3.18

WORKDIR /app

COPY /app/requirements.txt /app

RUN pip3 install -r requirements.txt

COPY ./app /app

EXPOSE 9701
CMD ["gunicorn", "-w", "4", "-b", "0.0.0.0:9701", "app:app"]