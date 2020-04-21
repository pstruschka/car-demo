# Toy Spring-Axon-MongoDB Demo
Basic API to try out Axon

### Start
Start MongoDB and AxonServer in docker

``docker run -d --name mongodb -p 27017:27017 -v ~/mongodata/db:/data/db mongo``

``docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver``

### API

`GET /car`  
Lists all cars

`PUT /car`  
Create a car with random ID as a random UUID

`GET /car/{id}`  
Get a car

`PUT /car/{id}`  
Creates a car

`POST /car/{id}/start`  
Starts a car

`POST /car/{id}/stop`  
Stops a car

`GET /car/{id}/history[?type={create,start,stop}]`  
View log on a car

`GET /car/{id}/history/count[?type={create,start,stop}]`  
Count of all actions or actions of a specific type on a car

`GET /car/history[?type={create,start,stop}]`  
View log of all cars

`GET /car/history/count[?type={create,start,stop}]`  
Count of all actions or actions of a specific type on all cars