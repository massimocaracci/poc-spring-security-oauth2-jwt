# Spring Security OAuth2 + JWT Authorization Server + JPA

Prove of concept of a Spring Security application, implementing a OAuth2 authorization server that produces JWT access tokens.

### Building and running

Clean and Build the application with embedded gradle 
and Run the Spring Boot application as a packaged application:


```bash
$ ./gradlew clean build
$ java -jar build/libs/jwt-auth-server-0.0.1-SNAPSHOT.jar
```

Another option could be to import the project using your IDE and chose to build and Run it there.

Once the application is running, the authorization server will be listening
for requests on port 9999.

### Obtaining an access token

This authorization server uses in-memory stores for both users and clients.
In-memory stores were chosen to simplify the example. In a real production-ready
scenario, you should use a different store (JDBC-based, for example).

There are two users:

 - Username: `pantasoft`; Password: `password`
 - Username: `massimo`;   Password: `password`

There is only one client:

 - Client ID: `my-trusted-client` 
 - Client Secret: `secret`

##### Using the `curl` command line tool, you can request a token as follows
(for the "habuma" user):

```bash
$ curl http://localhost:9999/oauth/token \
    -d"grant_type=password&username=pantasoft&password=password" \
    -H"Content-type:application/x-www-form-urlencoded; charset=utf-8" \
    -u my-trusted-client:secret
```

### Decoding the token

JWT tokens are actually three JSON objects that carry claim information about the
authorization, encoded into a `String`. 
That `String` is decoded by the resource server(and validated with the token's signature) to determine if the token carries
adequate authority to perform a request. 
But you can decode it and see its payload by pasting the token into a form at https://jwt.io/.

For example, a token issued by the authorization server for the "izzy" user might
look like this:


```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzUyMjM2MzQsInVzZXJfbmFtZSI6Iml6enkiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYTlkNjQ4OWUtZmI2ZC00NGE5LTlkMzUtMThlOWYzODUxNTcyIiwiY2xpZW50X2lkIjoibXljbGllbnQiLCJzY29wZSI6WyJyZWFkIl19.ADWvi_RvL1IQz4rfduhduAWVt0aDB8LfsP6ewlTQ2sQ
```

As you can see, it's not very human readable. That is, until you paste it into the
form at https://jwt.io. Doing so yields the following decoded header:


```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

This tells us that the token is a JWT token, encoded with "HS256" encoding. As for
the payload:

### Payload:
```json
{
  "exp": 1561480007,
  "user_name": "pantasoft",
  "authorities": [
    "ROLE_ADMIN",
    "ROLE_USER"
  ],
  "jti": "06a538e1-d322-4fbd-931f-30ca2b71fc75",
  "client_id": "my-trusted-client",
  "scope": [
    "read"
  ]
}
```

This tells us (among other things) that the token expires at 1561480007ms and was
issued for the user named "pantasoft" who has "ROLE_ADMIN", "ROLE_USER" authorities and OAuth2 "read" scope.

The third component is the signature that is used to validate the contents. This
helps the resource server know that the token was issued by a trusted authorization
server and not synthesized in a malicious attempt to gain access to the resource
server's resources.

== Obtaining an access token via Client Credentials Grant

If the resources your client is accessing are not user-specific, the client
can obtain a token via Client Credentials Grant.

Using the `curl` command line tool, you can request a token as follows:

```bash
$ curl http://localhost:9999/oauth/token \
    -d"grant_type=client_credentials" \
    -H"Content-type:application/x-www-form-urlencoded; charset=utf-8" \
    -u my-trusted-client:secret
```

### Obtaining an access token via Authorization Code Grant

Optionally, you can use Authorization Code Grant to obtain an access
token. This grant flow is most suitable when the client is a web application,
or a similar client. (Alexa skills can act as a connected client using
Authorization Code Grant, for example.)

First, point your browser to the authorization URL:

```
http://localhost:9999/oauth/authorize?client_id=my-trusted-client&response_type=code&redirect_uri=http://localhost:9191/x
```

If you've not already done this and haven't logged in, you'll be prompted to
login. Use either "pantasoft" and "password" or "massimo" and "password" as your
credentials.

Next, you'll be prompted to either authorize the client or deny authorization.
Assuming that you accept authorization and submit the form, you'll be redirected
to a bogus URL at http://localhost:9191/x with an authorization code in the URL
as a `code` parameter. (In a real application, this redirect URI would be a resource
on your application that accepts the code and exchanges it for an access token.)

Finally, make a POST request to the oauth/token path on the authorization server
to exchange the authorization code for an access token ("gn2hsi" in this example):

```bash
$ curl localhost:9999/oauth/token \
     -H"Content-type: application/x-www-form-urlencoded" \
     -d'grant_type=authorization_code&redirect_uri=http://localhost:9191/x&code=gn2hsi' \
     -u my-trusted-client:secret
```

The access token will be returned in the response.

### Obtaining an access token via Implicit Grant

Finally, another way to obtain an access token is via Implicit Grant.
This grant flow is best for situations when it would be awkward or impossible
to exchange an authorization code for a token and you just want to get the
token directly after authorization. (Alexa skills can also use Implict Grant
to obtain access tokens when connecting to an external API.)

First, point your browser to the authorization URL:

```
http://localhost:9999/oauth/authorize?client_id=my-trusted-client&response_type=token&redirect_uri=http://localhost:9191/x
```

If you've not already done this and haven't logged in, you'll be prompted to
login. Use either "pantasoft" and "password" or "massimo" and "password" as your
credentials.

Next, you'll be prompted to either authorize the client or deny authorization.
Assuming that you accept authorization and submit the form, you'll be redirected
to a bogus URL at http://localhost:9191/x with the access token in the URL
as an `access_token` hash parameter. (In a real application, this redirect URI
would be a resource on your application that accepts the token for its own
user.)
