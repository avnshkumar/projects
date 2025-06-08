FROM node:23-alpine
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
EXPOSE 3100
ENTRYPOINT ["npm", "run", "local"]
