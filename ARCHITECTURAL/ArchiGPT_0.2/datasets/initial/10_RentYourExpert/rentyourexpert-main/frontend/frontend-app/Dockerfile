FROM node:18-alpine

WORKDIR /user/src/app 

COPY ./package.json ./
COPY ./package-lock.json ./

RUN npm install
RUN npm install react-router-dom
RUN npm install mdb-react-ui-kit
RUN npm install react-icons

COPY . .

EXPOSE 3000

CMD ["npm","start"]
