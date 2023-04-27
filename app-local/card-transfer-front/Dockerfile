FROM node:13.12.0-alpine

COPY ./card-transfer-master/package.json ./
COPY ./card-transfer-master/package-lock.json ./
RUN npm install --silent

RUN npm install
COPY ./card-transfer-master/ ./
EXPOSE 3000
ENTRYPOINT ["npm","run","start"]


