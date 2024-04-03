FROM node:latest
EXPOSE 8080
WORKDIR /app
COPY . ./
RUN chmod +x wait-for-it.sh
RUN npm install
CMD ["./wait-for-it.sh", "rabbitmq:5672", "--", "node", "server.js"]
