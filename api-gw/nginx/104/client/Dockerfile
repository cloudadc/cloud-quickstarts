FROM node:18-alpine
LABEL maintainer "kylinsoong.1214@gmail.com"
ENV NODE_ENV=production

WORKDIR /app

COPY ["package.json", "package-lock.json*", "./"]

RUN npm install ws --production

COPY ["client.js", "./"]

CMD ["node", "client.js"]

