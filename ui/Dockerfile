FROM node:10
COPY package*.json  /ui/
COPY webpack.config.js /ui/
COPY tsconfig.json /ui/
COPY src /ui/src
WORKDIR /ui/
RUN npm install
CMD ["npm","start"]
EXPOSE 8080