FROM node:16

# create app directory
WORKDIR /usr/src/app

# install app depdendencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where avaliable (npm@5+)
COPY package*.json ./

RUN npm install
# if you are building your code for production
# RUN npm ci --only=production

# Bundle app source
COPY . .

EXPOSE 4242
CMD [ "node", "server.js" ]