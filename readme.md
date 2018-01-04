Fountain Chunker
================

Application to Split large posted JSON array payloads into arbitrary chunks of elements and re-server them via a REST interface. It's effectively a big paging service. 

This is a Spring Boot application and can be run and modified according to the Spring Boot documentation here:https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
i.e `gradle bootRun`.

Posting arrays
--------------
Run the compiled JAR with embedded Tomcat:

`java -jar fountainChunker.jar`

POST data to:
`http://localhost:8090/chunks/`
Where the body is a JSON array. 

Response will look like:

```
	{
		id: "70a6b3ba-3695-4b0e-91f5-9c54a1571063",
		lastAccessed: "1434972921504",
		chunks: [2392],
		0:  {
			id: "0"
			fileRef: "dbb999bc-01a1-43dd-ae7a-33db970b0a41.json"
			json: null
		},
		1:  {
			id: "1"
			fileRef: "d20bc96c-9651-404e-bf42-e9e42ac00f02.json"
			json: null
		}
	}
```

Retrieving JSON 'Chunks'
------------------------
GET request to `http://localhost:8090/chunks/<UUID1>/<UUID2>.json`

Where the first UUID is the id from the response and the second UUID is the chunk fileRef from the response. 

Configurable Properties
-----------------------
These can be found in the application.properties file and can all be over ridden on the command prompt as per the Spring Boot documentation. 
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

Download and run a Mongo Instance(Docker instructions)
------------------------------------------------------


`docker run --name chunker-mongo -p 27017:27017 -d mongo --auth`

`docker exec -it chunker-mongo mongo admin`

`db.createUser({ user: 'chunker', pwd: 'password', roles: [ { role: "userAdminAnyDatabase", db: "admin" }, { role: "userAdminAnyDatabase", db: "admin" }, { role: "userAdminAnyDatabase", db: "admin" } ] });`

`db.createUser({ user: "test", pwd: "password1", roles: [ { role: "readWrite", db: "test" } ]})`

Check you can log in:

`docker run -it --rm --link chunker-mongo:mongo mongo mongo -u chunker -p password --authenticationDatabase admin chunker-mongo/test`

`db.getName(); // should be 'test'`

See the full instructions at: https://hub.docker.com/_/mongo/

Mongo GUI
---------

https://studio3t.com/


