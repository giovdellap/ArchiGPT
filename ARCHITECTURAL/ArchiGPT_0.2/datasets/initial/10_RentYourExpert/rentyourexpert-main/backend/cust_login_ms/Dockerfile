FROM python:3.10-alpine
WORKDIR /cust_login_ms
COPY requirements.txt /cust_login_ms
RUN pip3 install -r requirements.txt
copy cust_login_ms.py /cust_login_ms


ENV FLASK_APP cust_login_ms.py
ENV FLASK_ENV development
ENV FLASK_RUN_PORT 5003
ENV FLASK_RUN_HOST 0.0.0.0

EXPOSE 5003


CMD ["flask", "run"]