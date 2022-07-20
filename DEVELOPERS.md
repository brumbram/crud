# User API Dev Guide

## Building
#### Required software in local
- JDK version 11
- Maven 3.8.4
- Spring 2.7
#### For downloading the dependencies and building the application
```
mvn clean install
```
## Testing
#### For all tests
```
mvn clean test
```
## Deploying
#### Bring up local database using docker-compose. We are using PostgresSql

```
docker-compose up
```
#### Create database in local
#### In order to start using the application we need to create the database in local. We can use postgres CLI to connect to postgres. For reference please check <a href="https://www.codementor.io/@engineerapart/getting-started-with-postgresql-on-mac-osx-are8jcopb" target="_blank">PostgresSql setup</a>
#### And then proceed to create database.
```
postgres=# CREATE database crud;
```

#### Start the spring boot via command line application . 
#### The startup will also run the initial database migrations
```
mvn spring-boot:run
```

## Additional Information
