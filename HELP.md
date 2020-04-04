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
docker build -t maxmorev/minio-rest-api .

docker images
````
##### Run your bootiful app in docker
````
docker run  -p 8081:8080 \
-e MINIO_HOST='http://localhost' \
-e MINIO_ACCESS_KEY='MINIOACCESS' \
-e MINIO_SECRET_KEY='SUPERSECRET1234' \
-e MINIO_REST_ACCESS_KEY='MYMINIOJAVAACCESS' \
-e MINIO_REST_ENDPOINT='http://yourendpoint' \
--name minio-rest-api \
maxmorev/minio-rest-api
````

##### Test service:
```` 
http -f POST localhost:8081/io/upload/file/test/?key=MYMINIOJAVAACCESS file@./cyberpunk.jpg
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

docker push maxmorev/minio-rest-api

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
