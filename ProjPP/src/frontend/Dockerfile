FROM node:latest

WORKDIR /frontend

COPY ./package.json ./
COPY ./package-lock.json ./

RUN npm install
RUN npm install react-scripts@5.0.1 -g

COPY . .

EXPOSE 3000

CMD [ "npm", "start" ]