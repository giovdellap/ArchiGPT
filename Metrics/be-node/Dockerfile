FROM node:18.19-alpine3.19

WORKDIR /code

COPY package.json package.json
COPY package-lock.json package-lock.json

RUN npm install

COPY src ./src

CMD npm start 


