# Java minio rest-api

REST API:
method POST:
http://localhost:8080/io/upload/jpg/{bucket}/
body
MULTIPART_FORM_DATA
file --file to upload 
key --access key of API

method GET:
Request:
http://localhost:8080/io/jpg/{bucket}/{fileName}
Response:
byteArray MediaType of MediaType.IMAGE_JPEG_VALUE

#### Useful links

How to Set Up an Object Storage Server Using Minio on Ubuntu 16.04

https://www.digitalocean.com/community/tutorials/how-to-set-up-an-object-storage-server-using-minio-on-ubuntu-16-04
