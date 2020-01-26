# Microservices Blog Application

**A simple way to share you personal thoughts**

This is the [Monolith Blog Apllication](https://github.com/andrewmalyshev/monolith-blog) but with the reviewed architecture. The main features and the user interface are the same with previous version. But the architectural approach was completely changed. This is a proof of concept application, which demonstrates [Microservice Architecture Pattern](http://martinfowler.com/microservices/) using Spring Boot, Spring Cloud, PostgreSQL, MongoDB and Docker with Docker Compose.
With a pretty simple Angular user interface, by the way.

## Technologies used
- Java 8
- Maven
- Spring Boot
- Spring Cloud
- Spring Security
- Spring Data
- JJWT
- Lombok
- PostgreSQL
- MongoDB
- Angular 7
- Webpack
- Docker
- Docker Compose

## Functional services

[Monolith Blog Apllication](https://github.com/andrewmalyshev/monolith-blog) was decomposed into two core microservices. All of them are independently deployable applications, organized around certain business domains.

<img width="667" alt="Functional services" src="https://user-images.githubusercontent.com/8774751/73024357-1b930f00-3e36-11ea-9d50-7021be7fa1f4.png">

#### Auth users service
Contains general users logic: authorization or registration of users, delete, aprove or create the new admin users by exists admin, get user detailes.

Method	| Path	| Description	| User authenticated	| Available from UI
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
GET	| /users/list	| Get list of all users	| × | ×	
GET	| /users/get-name/{id}	| Get full user name by id	|  | ×
POST	| /users/add	| Register the new user	|   | 	×
POST	| /users/is_unique_email	| Check the email address	|  | ×
GET	| /users/id	| Get id of the current user	|   | 
GET	| /users/is-admin	| Check if current user is the admin	|   | 
POST	| /users/admin/add	| Register the new admin user	| × | ×
POST	| /users/change-approval	| Activate user by the admin	| × | × 


#### Blogs service
Gives the ability to performs actions with blogs, comments and likes/unlikes. This service allows to persist or delete and moderate user's blogs, comments and likes/unlikes.

Method	| Path	| Description	| User authenticated	| Available from UI
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
GET	| /blog/list_all	| Get list of all blogs	          | × | ×	
POST	| /blog/add	| Add the new blogpost by user	| × | × 
POST	| /blog/delete	| Delete selected blogpost	| × | × 
POST	| /blog/change-approval	| Moderate blogpost by admin	| × | × 
POST	| /comment/add	| Add the comment to the selected blogpost	| × | × 
POST	| /likesunlikes/add	| Like or unlike the selected blogpost	| × | ×

#### Notes
- Each microservice has its own database, so there is no way to bypass API and access persistance data directly.
- In this project uses two types of stores - NoSQL MongoDB(as a primary database for Blogs Service) and relational PostgreSQL DB(as a primary database for Auth Users Service). It is a polyglot persistence architecture (сhoose the type of db that is best suited to service requirements).
- Service-to-service communication is quite simplified: microservices talking using only synchronous REST API.
## Infrastructure services

<img width="685" alt="Infrastructure" src="https://user-images.githubusercontent.com/8774751/73126627-d0a40380-3fbd-11ea-8725-1ed3dbf84985.png">

### API Gateway
There are two core services, which expose external API to client. In a real-world systems, this number can grow very quickly as well as whole system complexity. Actually, hundreds of services might be involved in rendering of one complex webpage.

In theory, a client could make requests to each of the microservices directly. But obviously, there are challenges and limitations with this option, like necessity to know all endpoints addresses, perform http request for each piece of information separately, merge the result on a client side. Another problem is non web-friendly protocols which might be used on the backend.

Usually a much better approach is to use API Gateway. It is a single entry point into the system, used to handle requests by routing them to the appropriate backend service or by invoking multiple backend services and [aggregating the results](http://techblog.netflix.com/2013/01/optimizing-netflix-api.html). Also, it can be used for authentication, insights, stress and canary testing, service migration, static response handling, active traffic management.

With Spring Cloud we can enable edge service with one `@EnableZuulProxy` annotation. In this project Zuul uses to route requests to appropriate microservices. Here's a simple prefix-based routing configuration for Blogs service:

```yml
zuul:
  routes:
   blogs-service:
      path: /blog/**
      serviceId: BLOGS-SERVICE
      stripPrefix: false

```

That means all requests starting with `/blog` will be routed to Blogs service. There is no hardcoded address. Zuul uses [Service discovery](https://github.com/andrewmalyshev/microservices-blog/blob/master/README.md#service-discovery) mechanism to locate Blogs service instances.

### Service discovery

Another commonly known architecture pattern is Service discovery. It allows automatic detection of network locations for service instances, which could have dynamically assigned addresses because of auto-scaling, failures and upgrades.

The key part of Service discovery is Registry. I use Netflix Eureka in this project. Eureka is a good example of the client-side discovery pattern, when client is responsible for determining locations of available service instances (using Registry server) and load balancing requests across them.

With Spring Boot, you can easily build Eureka Registry with `spring-cloud-starter-netflix-eureka-server` dependency, `@EnableEurekaServer` annotation and simple configuration properties.

Client support enabled with `@EnableDiscoveryClient` annotation an `bootstrap.yml` with application name:
``` yml
spring:
  application:
    name: blogs-service
```

Now, on application startup, it will register with Eureka Server and provide meta-data, such as host and port, health indicator URL, home page etc. Eureka receives heartbeat messages from each instance belonging to a service. If the heartbeat fails over a configurable timetable, the instance will be removed from the registry.

Also, Eureka provides a simple interface, where you can track running services and a number of available instances: `http://localhost:8761`

#### Notes
Service Discovery mechanism needs some time after all applications startup. Any service is not available for discovery by clients until the instance, the Eureka server and the client all have the same metadata in their local cache, so it could take 3 heartbeats. Default heartbeat period is 30 seconds.

#### Feign
Feign is a declarative Http client, which seamlessly integrates with Ribbon and Hystrix. Actually, with one `spring-cloud-starter-openfeign` dependency and `@EnableFeignClients` annotation you have a full set of Load balancer, Circuit breaker and Http client with sensible ready-to-go default configuration.

Here is an example from Account Service:

``` java
@FeignClient("auth-users-service")
public interface UsersServiceClient {

    @GetMapping("/users/id/")
    int getUserIdFromToken();
    
}
```

- Everything you need is just an interface
- Above example specifies just desired service id - `auth-users-service`, thanks to autodiscovery through Eureka (but obviously you can access any resource with a specific url)

## Security
The authentication flow is simple as:
- the user sends a request with his credentials to get a token
- the server validates the credentials and sends back a token
- with every request, the user has to provide the token, and server will validate that token 

For validating user credentials, and issuing tokens uses `auth-user-service`.

A token is an encoded string, generated by the application (after being authenticated) and sent by the user along each request to allow access to the resources exposed by the application. [JSON Based Token (JWT)](https://jwt.io/) is a JSON-based open standard for creating access tokens.

The tokens validation performs at the `gateway-service` level, and the `auth-users-service` validates user credentials, and issues tokens.

## How to run all the things?
In this project uses Docker containers and Docker Compose as a tool for defining and running multi-container Docker application. 

<img width="630" alt="Docker" src="https://user-images.githubusercontent.com/8774751/73125799-afd6b080-3fb3-11ea-9ae3-003f3c43c491.png">

For start the application execute `docker-compose up` command from directory where located the `docker-compose.yml` file.
#### Before you start
- Install Docker and Docker Compose.
- Make sure to build the project: `mvn package`(for `ui` you don't do this because it is Angular application and it will build when container starts)

#### Important endpoints
- http://localhost:8080/login - login page of the UI
- http://localhost:8090 - Gateway
- http://localhost:8761 - Eureka Dashboard
