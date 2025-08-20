FROM node:20-alpine
WORKDIR /app
COPY package*.json ./
RUN npm ci --omit=dev || npm install --omit=dev
COPY src ./src
EXPOSE 8080
ENV PORT=8080
CMD ["node", "src/server.js"]
