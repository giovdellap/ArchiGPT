# pull official base image
FROM node:18-alpine

# set working directory
WORKDIR /usr/src/admin

# add `/usr/src/app/node_modules/.bin` to $PATH
ENV PATH /usr/src/admin/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json .
COPY package-lock.json .
RUN npm ci
RUN npm install react-scripts@5.0.1 -g --silent
RUN npm install react-router-dom
RUN npm install bootstrap
RUN npm install react-loading-skeleton
RUN npm install --save font-awesome

# start appcdc
CMD ["npm", "start"]