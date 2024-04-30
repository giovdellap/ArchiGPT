FROM python:3.10


WORKDIR /dummyServer

COPY /dummyServer/requirements.txt /dummyServer
RUN pip install -r requirements.txt

COPY ./dummyServer /dummyServer

EXPOSE 9703

CMD ["python3" , "dummyServer.py"]