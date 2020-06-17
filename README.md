# File sharing spring boot based demo app

Java 8 is needed to run this sample.

## Clone
```
git clone https://github.com/kshabalin/file-sharing-app.git
```
Run
```
cd file-sharing-app
mvn package
cd target
java -jar demo-0.0.1-SNAPSHOT-spring-boot.jar --uploads.directory=you-files-directory
```

## Access
Registration. This is the only public endpoint.
```
POST http://localhost:8080/register
```

List files
```
GET http://localhost:8080/api/file
```
JSON Response:
```
{
    "owned": [
        {
            "id": "3",
            "name": "file.txt"
        }
    ],
    "shared": []
}
```

Upload files
```
POST http://localhost:8080/api/file
```
JSON Response:
```
{
    "id": "3"
}
```

Download file associated with given id
```
GET http://localhost:8080/api/file/{id}
```

The endpoint for sharing a file between two users.
Accept a json object with two string properties: email and file id.
```
POST http://localhost:8080/api/share
```
JSON Request body:
```
{
    "email": "mail@mail.ru",
    "fileId": "1"
}
