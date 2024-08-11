FROM node:20-alpine
 
LABEL version="1.0"
LABEL description="EcomApp React Frontend Image"
COPY ./build /build
RUN npm install -g serve
ENTRYPOINT ["serve","-s","build"]
EXPOSE 3000