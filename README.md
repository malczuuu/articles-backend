# Articles Backend

Backend of a simple application for exploring multi-tenancy concepts.

Creates a multi-tenant environments for storing and managing articles of each user. Users are
authorized via configured headers, attached to HTTP request (for example by a reverse-proxy).

## Table of Contents

* [Configuration](#configuration)
* [Building Docker image](#building-docker-image)
* [Running on local machine](#running-on-local-machine)
* [Project repositories](#project-repositories)

## Configuration

* `realm-header`

  The name of a header holding the `realm` (tenant equivalent in project terminology).

  Environment variable name: `REALM_HEADER`.

  Default value: `X-Auth-Realm`.

* `userid-header`

  The name of a header holding the `userid`.

  Environment variable name: `USERID_HEADER`.

  Default value: `X-Auth-Userid`.

* `username-header`

  The name of a header holding the `username`.

  Environment variable name: `USERNAME_HEADER`.

  Default value: `X-Auth-Username`.

## Building Docker image

Building Docker image requires to first build jarfile on local machine, before `docker build` call.

```shell
$ ./gradlew clean build
$ docker build -t articles-backend .
```

## Running on local machine

Running locally requires MongoDB database with proper indexes setup to be up and running. Use
[`articles-app`][articles-app] repository to launch such database.

```shell
$ docker-compose up -d mongo
```

Such MongoDB is accessible on `127.0.0.1:27017` address and was initialized as replica set using
[these scripts][mongo-init-scripts].

To run the service itself use `bootRun` Gradle task.

```shell
$ ./gradlew bootRun
```

The application will be available on [`http://localhost:8080`](http://localhost:8080).

**Note**, that each endpoint would require proper authorization header to be attached. Therefore,
plain access via browser won't be possible.

Alternatively, build and launch the jarfile.

```shell
$ ./gradlew clean build
$ java -jar build/libs/articles-backend-1.0.0-SNAPSHOT.jar
```

Launching should work fine with most IDEs (tested with IntelliJ IDEA).

## Project repositories

* [`articles-app`][articles-app], which holds Docker environment setup.
* [`articles-backend`][articles-backend], which holds backend application.
* [`articles-webapp`][articles-webapp], which holds frontend application.

[articles-backend]: https://github.com/malczuuu/articles-backend

[articles-webapp]: https://github.com/malczuuu/articles-webapp

[articles-app]: https://github.com/malczuuu/articles-app

[mongo-init-scripts]: https://github.com/malczuuu/articles-app/blob/master/conf/mongodb
