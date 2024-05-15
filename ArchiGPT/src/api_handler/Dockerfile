FROM python:3

WORKDIR /api_handler

ENV FLASK_APP=server.py

ENV FLASK_ENV=development

COPY ./requirements.txt .

RUN pip install -r requirements.txt

COPY . .

EXPOSE 10001

CMD ["python3","-u","server.py"]
