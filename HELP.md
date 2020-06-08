# Read Me First
The steps for building this project ant pull to kubernetes:
### Read how to configure nginx ingress loadBalancer
https://www.digitalocean.com/community/tutorials/how-to-set-up-an-nginx-ingress-with-cert-manager-on-digitalocean-kubernetes

use ingress.yaml as example

#### How to add volume to minio service and setup minio server
example of vlume
````
kubectl apply -f volume.yaml
````
#### check volumes
````
kubectl get pv
````
#### Read minio docs 
https://github.com/minio/minio/blob/master/docs/orchestration/kubernetes/k8s-yaml.md

then use minio-deployment.yaml to deploy minio in your kube cluster

#### Test app local:
````
maxmorev/minio-rest-api:0.0.2 .
docker build -t maxmorev/minio-rest-api:0.0.2 .

docker images
````
##### Run your bootiful app in docker
````
docker run  -p 8085:8080 \
-e MINIO_HOST='http://192.168.199.5' \
-e MINIO_ACCESS_KEY='AKA47EXAMPLEDOC' \
-e MINIO_SECRET_KEY='JAVATESTEXAMPLEKEY' \
-e MINIO_REST_ACCESS_KEY='AKA47EXAMPLEDOC' \
-e MINIO_REST_ENDPOINT='http://localhost:8085' \
--name minio-rest-api \
maxmorev/minio-rest-api
````

##### Test service:
```` 
http -f POST localhost:8081/io/upload/file/test/?key=MYMINIOJAVAACCESS file@./pictureExample.jpg
````
##### Response:
````
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Mon, 30 Mar 2020 18:37:07 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "message": null,
    "status": "OK",
    "uri": "http://yourendpoint/io/file/test/cyberpunk.jpg"
}
````
#### helpful docker commands
`````
docker container exec -it minio-rest-api sh

docker login
docker push maxmorev/minio-rest-api:0.0.2

kubectl apply -f minio-rest-api-deployment.yaml
`````

### check you kubectl
````
kubectl get pods

kubectl apply -f secret.yaml

kubectl get secret minio-rest-api-secrets -o yaml

kubectl apply -f service.yaml

kubectl create deployment minio-rest-api --image=maxmorev/minio-rest-api

kubectl get pods
````

Execute shell in da pod:
````
kubectl exec -t -i minio-rest-api-66d8656456-wxrmw sh
````

Automate your Builds on Docker Hub by Writing a Build Hook Script!
https://codeclimbing.com/automate-your-builds-on-docker-hub-by-writing-a-build-hook-script/
