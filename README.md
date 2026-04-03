# OC-P11-PoC


## Start project

Run database with docker
```bash
docker compose up -d database
```

Run backend with java 21 and gradle
```bash
cd emergency-bed-service
./gradlew bootRun
```

Run frontend with node 22 
```bash
cd front-end
npm install
npm start # or `npm run dev`
```



## Back end

- [README Backend](./emergency-bed-service/README.md)

## Front end

- [README Frontend](./front-end/README.md)