# JwtDemo


### 1. JWT Token Request 

| Particulars   | Value         |
| ------------- |:-------------:|
| URL           | localhost:8080/token |
| Body          | {"userName":"user1","password":"password"}|
| Header        | Content-Type: application/json|


### 2. Hello Greeting End Point Request

| Particulars   | Value         |
| ------------- |:-------------:|
| URL           | localhost:8080/hello |
| Header        | Authorization: Bearer <token> |
