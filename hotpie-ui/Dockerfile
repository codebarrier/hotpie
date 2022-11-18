# Specify a base image
FROM node:14-alpine as node

WORKDIR /usr/app

# Install some depenendencies
COPY ./package.json ./
RUN npm install
COPY ./ ./
RUN npm run build --prod

#stage 2
FROM nginx:alpine
COPY --from=node /usr/app/dist/hotpie-ui /usr/share/nginx/html
COPY ./nginx.proxy.conf /etc/nginx/conf.d/default.conf

# Default command
# CMD ["npm", "start"]
