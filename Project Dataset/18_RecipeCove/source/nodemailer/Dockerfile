FROM node:latest

ENV NODE_ENV=production

WORKDIR /nodemailer/

COPY package*.json ./

COPY . .

RUN chmod +x ./wait-for-it.sh

RUN npm install --production

CMD ["./wait-for-it.sh", "rabbitmq:5672", "--", "node", "app.js"]
