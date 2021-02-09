# Employee Inventory Management

## Requirements

For building and running the application you need:

- [JDK 11]
- [Maven 3.6]

## Compile and package

```shell
mvn clean install
```

## Running the application locally

Execute the `main` method in the `EmployeeInventoryApplication` class from your IDE.

## Running using Docker

1. `mvn clean install`
2. `docker build -t employee-inventory .` at root folder
3. Run docker image `docker container run -p 8080:8080 --name employee-inventory employee-inventory`

You can access the application on `http://localhost:8080/swagger-ui.html`

## Running using Kubernetes
1. Docker Image is pushed to https://hub.docker.com/repository/docker/aonkar/employee-inventory
2. deployment-definition.yaml for Deployment of the pod in kubernetes cluster
3. service-definition.yaml for making the service available to the external world

## API Documentation
###### Swagger `http://localhost:8080/swagger-ui.html`

## H2 Database console
`http://localhost:8080/h2-console/`

username: username

password: password


  
  
