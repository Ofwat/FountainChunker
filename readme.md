Fountain Chunker
================

Application to Split large posted JSON array payloads into arbitrary chunks of elements and re-server them via a REST interface.

Posting arrays
--------------
Run the compiled JAR with embedded Tomcat:
java -jar fountainChunker.jar
POST data to:
http://localhost:8090/chunks/
Where the body is a JSON array. 

Response will look like:
	{
		id: "70a6b3ba-3695-4b0e-91f5-9c54a1571063"
		lastAccessed: 1434972921504
		chunks: [2392]
		0:  {
			id: "0"
			fileRef: "dbb999bc-01a1-43dd-ae7a-33db970b0a41.json"
			json: null
		}-
		1:  {
			id: "1"
			fileRef: "d20bc96c-9651-404e-bf42-e9e42ac00f02.json"
			json: null
		...

Retrieving JSON 'Chunks'
------------------------
GET to http://localhost:8090/chunks/1cbb6263-dfab-4bb8-b1c3-67fe71a82dfa/3377d44d-3ef5-4e15-928f-8f239d5cb325.json
Where the first UUID is the id from the response and the second UUID is the chunk fileRef from the response. 

Configurable Properties
-----------------------
TBA