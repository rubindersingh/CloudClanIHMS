# CloudClanIHMS
CloudClan Team is working to build a full stack images management solution over cloud where images can be uploaded/retrieved using REST API's with on-the- fly basic scaling and transformations to save bandwidth and render images with speed. 
 
# Technologies involved
Our Project relies on following technology stack:

1. REST Web Services : We are using Spring Framework to create REST endpoints for image uploads/downloads.

2. Cassandra : Cassandra is our main database which will hold users and images metadata and other application statistics.
   We chosen to use Cassandra as our backend database as it is very data read/write efficient and can handle petabytes of information with ease.
   
3. Redis : Redis is used to put caching layer for retrieving latest entries bypassing main database hit and storing other expensive results for fast data rendering. It is also being used for session persistence.

4. RabbitMQ: RabbitMQ is used for sending asynchronous tasks to backend workers for reducing user request holding time to minimum.

5. Swift Object Store: Swift object store is an open source solution for object storage and it is developed as part of Openstack family. Swift store is highly scalable and its cluster can contain thousands of server machines.


# Installation and Setup
* **Cassandra** and **Swift** store installation script and instructions can be found in project's **docs** directory.
* **Redis** cluster setup instructions are in **instructions** folder and **sample-conf** file is provided.
* **RabbitMQ** instructions docs is provided in **instructions** folder.
* **Nginx plus** can be downloaded from Nginx site. It is only a monthly trial version. Nginx **sample-conf** files has been provided.
* **Python Script** to check status and then take decision to add or drop nodes from load balancer is given in **scripts** folder.

# Buid and Deployment

- Following commands can be used to boot run this project:

```bash 
  cd /absolute/path/to/CloudClanIHMS
  ./gradlew build & java -jar build/libs/CloudClanIHMS-1.0.jar
  ```
  
- For deployment, build using **./gradlew build** command and migrate jar to server and run using **java -jar** command.

# Classes and Packages description

**/src/main/java :** This folder holds the REST web services java classes. It has following packages and classes. 

1. **com.asu.cloudclan.app** : This package has main spring class.
    - **CloudClanIhmsApplication.java** : Spring main starter class. 

2. **com.asu.cloudclan.app.conf** : This package holds java configuration files.
    - **AsyncExecuterConfiguration.java** : Thread executer pool classes for async tasks. 
    - **I18NConfiguration.java** : Internationalization configuration bean. 
    - **RabbitMQConfiguration.java** : It create beans for RabbitMQ listeners and senders. 
    - **RedisCacheConfiguration.java** : It creates beans for Redis cache. 
    - **SwiftStorageConfiguration.java** : It connects to swift and create required beans. 
    
3. **com.asu.cloudclan.controller** : This package holds all controller classes which listen at configured REST end points. This is the main entry point for http requests.
    - **ContainerController.java** : Provides REST end points for creating and sharing containers.
    - **HomeController.java** : Links URLs to Index, Home and Register page. 
    - **ImageController.java** : Provides REST end points for getting original or transformed images.
    - **RegistrationController.java** : Provides REST endpoint for user registration API.
    - **UploadController.java** : Provides REST endpoint for upload functionality. 
    
4. **com.asu.cloudclan.entity.cassandra** : This package holds all cassandra table entity classes.
    - **Container.java** : Entity class for container table.
    - **Image.java** : Entity class for storing user's image and transformations data. 
    - **ImageServiceUse.java** : Entity class for holding service use statistics like upload/download bandwidth use and transformations run.
    - **ImageStorageUse.java** : Entity class for storing storage use stats.
    - **User.java** : Entity class for storing user information. 
    - **UserContainer.java** : Entity class which holds containers information belonging to user along with access. 
    
5. **com.asu.cloudclan.enums** : This package holds all enums.
    - **AccessType.java** : Access types for containers like Author, Admin, Read Only etc.
    - **ContainerType.java** : Container type like Public or private. 
    - **ImageFormat.java** : Supported image formats and their MIME types.
    - **UploadStatus.java** : Upload status for images.
    
6. **com.asu.cloudclan.service** : This package holds all service classes which holds business logic of application.
    - **CassandraSessionService.java** : Cassandra Repo service for database session management.
    - **ContainerCoreService.java** : Cassandra Repo service for container specific queries.
    - **ImageCoreService.java** : Cassandra Repo service for image data specific queries.
    - **UserService.java** : Cassandra Repo service for user specific queries.
    - **CoreTransformationService.java** : Underlying core transformation service which provides interface for other services to access scala transformation service class.
    - **ScalaTransformationService.scala** : It holds main logic for running transformations on provided input stream using given transformation list.
    - **RabbitMQListenerService.java** : RabbitMQ listener end points for Worker servers.
    - **RabbitMQSenderService.java** : RabbitMQ send end points for sending info to Worker servers.
    - **RedisCacheStoreService.java** : Redis cache store and update end points for use by other service classes.
    - **SwiftStorageCoreService.java** : Swift storage core service which communicates with swift store.
    - **SwiftStorageService.java** : High level interface to access swift storage core service end points.
    - **ContainerGeneratorUtilService.java** : It is used for generating unique container id.
    - **ContainerService.java** : Main backing service class container controller which holds high level business logic for creating and sharing containers.
    - **ImageService.java** : Main backing service class for image conteoller for download and transformation functionality.
    - **RegistrationService.java** : Service class for registration controller.
    - **UploadService.java** : Service class for upload requests.
    
7. **com.asu.cloudclan.util** : This package holds all utility classes for common utility methods.
    - **JsonUtil.java** : Used to convert object to JSON string.
    - **SizeUtil.java** : Converts all upload, download and storage usage sizes to human readable format. 
    - **ValidationUtil.java** : Holds common validation methods like email check, container type check etc.
    
8. **com.asu.cloudclan.vo** : This package holds all data transter objects classes to pass on data from one class to another and also for user interface objects.
    - **COntainerVO.java** : For taking/returning container data to user.
    - **ErrorVO.java** : For passing error information in response. 
    - **FinalTransformationVO.java** : Internal data transfer object for sending tranformation info to scala class.
    - **HomeVO.java** : Model object for home page.
    - **ImageMetadataVO.java** : data transfer object for sending information to worker servers.
    - **ImageVO.java** : REST end point object for responding to upload request.
    - **PageVO.java** : REST end point object for pagination.
    - **RegistrationVO.java** : REST end point object for receiving registration request.
    - **RequestResponseVO.java** : Parent class for all request response VO classes.
    - **TransformationVO.java** : REST end point object for image transformation request.
    - **UploadVO.java** : REST end point object for receiving upload request.
    - **UsageDataVO.java** : Model object for usage data information for home page.

**/src/main/resources :** This folder holds the REST web services resources. It has following resource folders. 
   
1. **i18n** : This folder holds all internationalization message properties files.

2. **static** : This folder holds all static images/js/css files.

3. **templates** : This folder holds all templates for application UI pages.
    - **home.html**
    - **index.html**
    - **register.html**
    
4. **application.yaml** : Application properties file.

    
# REST endpoint API description

**Registration**
```
//This api is openly accessible.

URL: http://<HOST>/registration

METHOD: POST

INPUT: 

{
    "emailId": "<Email Id>",
    "password": "<PASSWORD>", 
    "firstName": "<First name>",
    "lastName": "<Last Name>"
}

OUTPUT:
{
    "emailId": "<Email Id>",
    "password": "<PASSWORD>", 
    "firstName": "<First name>",
    "lastName": "<Last Name>",
    "errorVOs" : [ //Will only come if error occured
        {
            "fieldId" : <Field in which error has occured>
            "message" : <error message>
        },
        {
            "message" : <error message>
        }
    ]
}
  ```


**Create Container**
```
//This API needs authentication with HEADER "x-auth-token"

URL: http://<HOST>/containers

METHOD: POST

INPUT: 

{
    "name": "<NAME>",
    "type": "<TYPE>", //Only PUBLIC & PRIVATE
}

OUTPUT:
{
    "id" : "<ID>",
    "name": "<NAME>",
    "type": "<TYPE>",
    "errorVOs" : [ //Will only come if error occured
        {
            "fieldId" : <Field in which error has occured>
            "message" : <error message>
        },
        {
            "message" : <error message>
        }
    ]
}
  ```


**Share Container**
```
//This API needs authentication with HEADER "x-auth-token"

URL: http://<HOST>/containers/share

METHOD: POST

INPUT: 

{
    "id": "<ID>",
    "type": "<Access TYPE>", //Only AD(Admin), R(Read only), W(Write only), RW(Read and write both) supported
    "emailId": <Registered Other User Email>
}

OUTPUT:
{
    "id" : "<ID>",
    "type": "<Access TYPE>",
    "emailId": <Registered Other User Email>,
    "errorVOs" : [ //Will only come if error occured
        {
            "fieldId" : <Field in which error has occured>
            "message" : <error message>
        },
        {
            "message" : <error message>
        }
    ]
}
  ```
  

**Image Upload**
```
//This API needs authentication with HEADER "x-auth-token"

URL: http://<HOST>/images/

METHOD: POST

INPUT: 

{
    "containerId": "<Container ID>", //To which image should be associated
    "keepOriginal": "<true or false>", //if true original image will be saved else after 20% compression of original size
    "files": <MULTIPART FILE>, //Include as many times you want to upload num of images
    "files": <MULTIPART FILE>,
    "files": <MULTIPART FILE>,
    "files": <MULTIPART FILE>,
    "files": <MULTIPART FILE>
}

OUTPUT:
{
    "containerId" : "<Container ID>",
    "keepOriginal": "<Specified Value>",
    "imageVOs" : [ 
            {
                "name" : <Name of file>
                "url" : <Url of file>
                "status" : <Status of upload for this file> //COMPLETED, SKIPPED, FAILED
                "message" : <error message if any>
            },
            {
                "name" : <Name of file>
                "url" : <Url of file>
                "status" : <Status of upload for this file> //COMPLETED, SKIPPED, FAILED
                "message" : <error message if any>
            }
        ]
}
  ```
  
  
**Image Download**
```
//This API needs authentication with HEADER "x-auth-token" for private container
//No auth required for public containers

URL: http://<HOST>/images/{state}/{containerId}/{ImageURL}?<QUERY_PARAMS>

METHOD: GET

INPUT: 

state: 'p' or 't' // if 'p', transformed image will be stored for next time else discarded
containerId: ContainerId of image container.
ImageURL : Image URL returned in previous API.
QUERY_PARAMS:
    - w : For specifying width
    - h : For specifying height
    - op : For specifying operation, valid values are 'fit', 'cover', 'resize'
    - fit_c : For specifying fit op color if padding needs to be added for fitting then what color, format is R,G,B,ALPHA
    - pad : For specifying padding, at most can have 4 values comma seperated.
    - pad_c : For specifying pad color, format is R,G,B,ALPHA
    - fltr : For specifying filter, valid values are blur, grayscale, lensblur, sepia, vintage etc
    - flip : For specifying flip axis, valid values are x, y
    - rot : For specifying rotaton, format is L or L,L,R etc
    - scale: for scaling
    

OUTPUT:
Tranformed image else error if not found.
  ```