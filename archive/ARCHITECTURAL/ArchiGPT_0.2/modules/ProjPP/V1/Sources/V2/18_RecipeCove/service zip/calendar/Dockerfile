FROM  node:latest

WORKDIR /calendar/

COPY package*.json ./

RUN npm install 

COPY . .

EXPOSE 3001

CMD ["npm", "start"]