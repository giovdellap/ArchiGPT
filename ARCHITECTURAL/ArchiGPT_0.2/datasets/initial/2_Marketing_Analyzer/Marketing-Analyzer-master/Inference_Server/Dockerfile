FROM python:3.7-slim

WORKDIR /backend/inference

COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt

COPY . .

EXPOSE 8080
ENTRYPOINT [ "python3", "main.py"]
