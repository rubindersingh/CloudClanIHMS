# CloudClanIHMS
CloudClan Team is working to build a full stack images management solution over cloud where images can be uploaded/retrieved using REST API's with on-the- fly basic scaling and transformations to save bandwidth and render images with speed. 
 
# Technologies involved
Our Project relies on following technology stack:

1. REST Web Services : We are using Spring Framework to create REST endpoints for image uploads/downloads.

2. Cassandra : Cassandra is our main database which will hold users and images metadata and other application statistics.
   We chosen to use Cassandra as our backend database as it is very data read/write efficient and can handle petabytes of information with ease.
   
3. Redis : Redis is used to put caching layer for retrieving latest entries bypassing main database hit and storing other expensive results for fast data rendering.

4. RabbitMQ: RabbitMQ is used for sending asynchronous tasks to backend workers for reducing user request holding time to minimum.

5. Swift Object Store: Swift object store is an open source solution for object storage and it is developed as part of Openstack family. Swift store is highly scalable and its cluster can contain thousands of server machines.


# Installation and Setup
* Cassandra and Swift store installation script and instructions can be found in project's **docs** directory.
* Redis cluster setup script will be added soon.

# Buid and Deployment

- Following commands can be used to boot run this project:

```bash 
  cd /absolute/path/to/CloudClanIHMS
  ./gradlew build & java -jar build/libs/CloudClanIHMS-1.0.jar
  ```
  
- For deployment, build using **./gradlew build** command and migrate jar to server and run using **java -jar** command.