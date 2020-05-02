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
src @
```

2. Subscribe to the API and get an access token.

3. Replace <token> with the obtained token.

4. Start the application.
```
./mvnw spring-boot:run
```
