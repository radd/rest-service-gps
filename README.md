# Real-time GPS tracking - REST service

This project is part of the real-time tracking system. 
[GPS tracking system](https://github.com/radd/rest-service-gps), [Android App](https://github.com/radd/gps-android-app), [Web App](https://github.com/radd/gps-webapp-nodejs)

Spring Boot + REST + JWT + WebSocket (STOMP) + MongoDB

REST service includes:
- Login system (JWT)
- WebSocket communication (with STOMP protocol)
- MongoDB - nosql database for store GPS coordinates 

System architecture:

<img src="https://radd.github.io/other/images/gps-rest/architecture.jpg" width="700" >

Client applications:

<img src="https://radd.github.io/other/images/gps-rest/screens.jpg" width="700" >

MongoDB collections:

<img src="https://radd.github.io/other/images/gps-rest/mongodb_schema.jpg" width="450" >
