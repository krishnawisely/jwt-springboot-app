# JWT with RSA256 signature in springboot app
### Abstract
Generating and verify token with RSA256 keypair and secure resources by configuring token verification logic in interceptors.
### Features
- Authentication
  <br />Checking whether provider username and passord is matching or not. If it's matching generating JWT token.
- Authorization
  <br />Requesting server with JWT token to access resource. If JWT token is verified then allow request to access resource.
