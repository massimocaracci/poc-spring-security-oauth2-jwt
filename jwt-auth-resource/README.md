# Spring Security OAuth2 + JWT Resource Server

Prove of concept of a Spring Security OAuth2 and Spring MVC to provide an API that is secured by an OAuth2 resource server that accepts JWT access tokens.

### Building and running

Clean and Build the application with embedded gradle 
and Run the Spring Boot application as a packaged application:


```bash
$ ./gradlew clean build
$ java -jar build/libs/jwt-auth-resource-0.0.1-SNAPSHOT.jar
```

Another option could be to import the project using your IDE and chose to build and Run it there.

### Consuming the API

The API provided by this application has two endpoints:

- `/unsecured` - An unsecured endpoint that requires no access token.
- `/secured` - A secured endpoint that can only be access with a valid JWT access
   token in the request.
   
Using `curl`, you should be able to consume the `/unsecured` endpoint without any 
restriction:

```bash
$ curl localhost:8080/unsecured
```
This is an unsecured resource

If, however, you attempt to consume the `/secured` endpoint without providing a
valid access token:

```bash
$ curl localhost:8080/secured
```
you will receive an HTTP 401 (Unauthorized) response:
```json
{"error":"unauthorized","error_description":"Full authentication is required to access this resource"}
```

In order to successfully access the `/secured` endpoint, you'll need to provide
a valid access token in the `Authorization` header of the request. Assuming that you
have followed the instructions in the jwt-auth-server project's README to obtain
an access token, you can use it to make a successful request with `curl` like this:

```bash
$ curl localhost:8080/secured -H"Authorization: Bearer <<ACCESS_TOKEN>>"
```
you should get:
 ```
 - status 200 ok
 - This is a SECURED resource. Authentication: pantasoft; Authorities: [ROLE_USER, ROLE_ADMIN]
 ```
 
 In alternative you can use postman or similar IDE to Get the new Access Token from the `jwt-auth-server` and then access to the private resource to `jwt-auth-resource`. 