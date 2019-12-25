FROM node:10
WORKDIR /usr/src/app
COPY src/ package*.json webpack.config.js tsconfig.json ./
RUN npm install
EXPOSE 8080
CMD [ "npm", "start" ]