# ReadingIsGood
ReadingIsGood is an online books retail firm which operates only on the Internet. Main
target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day.

## Tech Stack

- Java 17
- Spring Boot
- PostgreSQL
- Maven
- Docker

## Running the project

To start project follow these instructions:
```shell
mvn clean install

docker-compose up
```

If you encounter any errors while dockerizing, you can try the following:

```shell
docker-compose down

mvn clean install -DskipTests

docker-compose up --build
```

## Integration tests may fail due to database connection. If so, you can follow these steps.

```shell
mvn clean install -DskipTests

docker-compose up
```

If you encounter any errors while dockerizing, you can try the following:

```shell
docker-compose down

mvn clean install -DskipTests

docker-compose up --build
```

After these steps the backend service will run at `http://localhost:8080`

After deployment, you can test and get information about through the Swagger Documentation from link above:
- **Swagger UI:** [Open API Endpoint](http://localhost:8080/swagger-ui/index.html#/)
- **Postman Collection:** [Download](https://raw.githubusercontent.com/anilized/reading-is-good/main/doc/Getir.postman_collection.json)

## Connecting to Database

- You can connect the database locally with the connection informations on `application.properties`
  - ```shell
    spring.datasource.url=jdbc:postgresql://localhost:5432/getir
    spring.datasource.username=postgres
    spring.datasource.password=
    ```
- You can connect dockerized PostgreSQL database from your local machine with the following
  - ```shell
    host=localhost
    port=5435
    database=getirDB
    username=getirDB
    password=getirDB
    ```

## Usage

### Registration and Login

- Application created based on Authentication and Authorization.
- Role based authentication implemented. There are two auth types: **admin** and **customer**. 
- Authentication and Authorization operations can be found under this endpoint:
- `http://localhost:8080/api/auth`
- There are two POST endpoints, one registration and one for login to get JWT token. 

## Steps to creating users

- Application checks `roles_table` table to see if requested role available for the application. Because of this, firstly we will run this script on database:
  - ```shell
      INSERT INTO roles_table(name) VALUES('ROLE_CUSTOMER');
      INSERT INTO roles_table(name) VALUES('ROLE_ADMIN');
    ```
- After successfully inserting data, you can register user from `http://localhost:8080/api/auth/signup` (See postman collection for example request).
- Lastly, get your Bearer from `http://localhost:8080/api/auth/signin` with `username` and `password` and use generated token on `admin allowed` operations to call requests.
- Also don't forget to take note of your `password` because its encrypted.

## Steps to Create User with Customer Role:

**You can create `customer` role based user with two ways. You can create with an `admin` account or directly `from http://localhost:8080/api/auth/signup`** <br/>
**Both examples created on Postman Collection. You can check requests from Postman Collection or Swagger Docs**

- Creating user with `customer` role either with an `admin` account.
  - To create user with `admin` account you can send request to `http://localhost:8080/api/customer` with `email`, `name` and `surname`.
  - No `password` needed on request because password will automatically mapped with encryption to their `email`.
  - After that you can login to customer account with `email` and `password`.
- Creating user with customer role directly from `http://localhost:8080/api/auth/signup`.
  - Same steps witth creating an `admin` account. (See `Steps to creating user`).
  - After that you can login to customer account with `email` and `password`.

### Authentication & Authorization

The most of the endpoints are secured with below example.

> **Authentication:** Bearer <your_token>

Tokens will expire **24 hours** after creation.

> **You don't have to create users again and again. User information holds on database so you can login with same users to test**

#### Authorized Endpoints

| Method | Endpoint                      | Authority |
|--------|-------------------------------|-----------|
| ANY    | **/api/customer/**            | ADMIN     |
| ANY    | **/api/book/**                | ADMIN     |
| ANY    | **/api/statistics/**          | ADMIN     |
| POST   | **/api/order**                | CUSTOMER  |
| GET    | **/api/order/{id}**           | ADMIN     |
| GET    | **/api/orders-between-dates** | ADMIN     |


## Application Architecture

Multi layered architecture was used for this project.<br/>
You can see the architecture below.

```.
├── auth                    # Authentication infrastracture.
├── config                  # Configurations.
├── controller              # Controller layer.
├── data                    # Business models, DTOs, Repository layer.
├── logging                 # Logging infrastructure.
└── service                 # Service layer. 
```

## How I Solved Stock Consistency

Based on the given information stock consistency was the most important challenge to solve.

This challenge can be solved either using Pessimistic Locking or Optimistic Locking.

Basically, Optimistic Lock checks the version of the row and checks if it's changed before our transaction. If it's not application will continue to process and update version<br/> On the other hand, Pessimistic Lock actually locks the row completely from beginning to the end of transaction.

On our case I found that Optimistic Locking will be the most suitable way to overcome this problem because:
- Conflicts mostly happens infrequently
- Optimistic Locking can be served multiple users at once easily
- Deadlock situations won't happen
- Better performance based on Pessimistic Locking
- Pessimistic Locking may affect application scaling
- Pessimistic Locking uses much more resource and may increase response times.

Also, a retry mechanism added for Optimistic Locking.

> When an order request is received from more than one user at the same time for the same book, only one of them will recevice succes. Any other requests got an error will be automatically retried until they succeed or the repeat limit is exceeded.

Thanks to the ORM tools' `transaction process`, fail scenarios will automatically rollback.