# IBM Translator Api :globe_with_meridians:
An Api that translates text from one language to another using the IBM Watson Language Translator's Api and it is deployed on Heroku platform

![Top language](https://img.shields.io/github/languages/top/neemiassgc/ibm-translator-api)
![Code size](https://img.shields.io/github/languages/code-size/neemiassgc/ibm-translator-api)
![Repo size](https://img.shields.io/github/repo-size/neemiassgc/ibm-translator-api)
![License](https://img.shields.io/github/license/neemiassgc/ibm-translator-api)
![Last commit](https://img.shields.io/github/last-commit/neemiassgc/ibm-translator-api/main)
![Deploy](https://img.shields.io/static/v1?label=deployed%20on&message=Heroku&color=79589f&logo=heroku&logoColor=79589f)
![Build tool](https://img.shields.io/static/v1?label=Build%20tool&message=Gradle&color=02303a&logo=gradle&logoColor=02303a)
![Java version](https://img.shields.io/static/v1?label=Java%20version&message=15&color=red&logo=java&logoColor=red)
![Framework](https://img.shields.io/static/v1?label=Framework&message=Spring&color=green&logo=spring&logoColor=green)

## Endpoints preview :game_die:
> Url base => `https://secret-eyrie-64268.herokuapp.com/`

Method | Endpoint | Request body | Response body
-------|----------|--------------|--------------
GET | `/languages` | none | application/json
POST | `/translate` | application/json | application/json

## Core technologies :hammer_and_wrench:
> <img height="42" alt="json" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/json.svg"/>
> <img height="42" alt="heroku" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/heroku.svg"/>
> <img height="42" alt="java" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/java.svg"/>
> <img height="42" alt="gradle" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/gradle.svg"/>
> <img height="42" alt="springboot" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/springboot.svg"/>
> <img height="42" alt="spring" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/spring.svg"/>
> <img height="42" alt="springsecurity" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/springsecurity.svg"/>
> <img height="42" alt="junit5" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/junit5.svg"/>
> <img height="42" alt="ibm" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/ibm.svg"/>
> <img height="42" alt="ibmwatson" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/ibmwatson.svg"/>
> <img height="42" alt="intellijidea" width="42" src="https://static-content-c7a9e.firebaseapp.com/icons/svg/intellijidea.svg"/>

## Run locally :computer:
```sh
git clone https://github.com/neemiassgc/ibm-translator-api.git
cd ibm-translator-api
./gradlew build -x check
java -jar build/libs/translator-0.0.1-SNAPSHOT.jar
```
Open [http://localhost:5000](http://localhost:5000) to view it in the browser.

## License :memo:
This project is under the Apache License
