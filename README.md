# Java minio rest-api
Rest API for upload/view files with Object Storage Server Minio
useful link: https://httpie.org/doc
###REST API:
##### Method POST:
~~~
http -f POST localhost:8080/io/upload/file/<bucketName>/?key=MYMINIOJAVAACCESS file@./cyberpunk.jpg
~~~
##### Responses
~~~
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Sat, 04 Apr 2020 12:08:19 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "message": null,
    "status": "OK",
    "uri": "http://io.titsonfire.store/io/file/<bucketName>/cyberpunk.jpg"
}


Status 500 fail
{ status: "error", uri: "http://localhost:8080/io/file/{bucket}/{originalFileName}", message: "404 Example error message"}
~~~

###Method GET:
##### Request:
http://localhost:8080/io/file/{bucket}/{fileName}
~~~
HTTP/1.1 200 
Connection: keep-alive
Content-Length: 471982
Content-Type: image/jpeg
Date: Sat, 04 Apr 2020 12:09:29 GMT
Keep-Alive: timeout=60

~~~
#### Useful links

How to Set Up an Object Storage Server Using Minio on Ubuntu 16.04:

https://www.digitalocean.com/community/tutorials/how-to-set-up-an-object-storage-server-using-minio-on-ubuntu-16-04

Or Deploy MinIO on Kubernetes:

https://docs.min.io/docs/deploy-minio-on-kubernetes.html

#### FOR MORE INFORMATION SQUEEZE READ HELP.MD