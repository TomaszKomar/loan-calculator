### Loan calculator

To run the backend project use `./mvnw.cmd spring-boot:run`

API will be available at port 8080

To run the backend tests use `./mvnw.cmd test`

Start react app by `cd loan-calculator` next `npm install` and then `npm start` - npm start should also launch a browser window. 
React app expects API to be running at port 8080 and is available at port 3000.

Use `http://localhost:8080/h2-console` to access H2 in memory db, connection details available in `src/main/resources/application.properties`