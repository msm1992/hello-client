# How to use hello-client to make WSO2 API Request

1. Create an API in WSO2 APIM 3.1.0 with following attributes.

```
name : any string
context : test
version : 1.0
```

Resources :
```
 /hello with a string query parameter 'name'
```

Backend : 
```
clone https://github.com/msm1992/hello-service and start the server
backend URL for APIM : http://localhost:8080/
```

2. Subscribe to the API and get an access token.

3. Replace <token> @https://github.com/msm1992/hello-client/blob/master/src/main/java/com/example/tracing/hello/client/HelloClientApplication.java#L63 with the obtained token.

4. Start the application.
```
./mvnw spring-boot:run
```
