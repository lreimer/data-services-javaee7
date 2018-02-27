# Data services: Processing big data the microservice way with Java EE 7

This repository contains the showcase for my talk at O'Reilly Architecture Conference NY 2018.
For details see: https://conferences.oreilly.com/software-architecture/sa-ny/public/schedule/detail/63967

The slides for this talk can be found on SpeakerDeck here: https://speakerdeck.com/lreimer/data-services-processing-big-data-the-microservice-way

# Usage

## Building the Data Services

The individual data services are all separate Gradle builds. Make sure to build these individually.

```
$ cd hazelcast/
$ ./gradlew clean build
```

## Running with Docker Compose

```
$ docker-compose up --build
```

## Setup Google Container Engine

- Install the gcloud SDK for Mac (or Windows)
- Make sure you have a project with billing activated as well as the container engine management API
- Make sure you have `kubectl` installed, either using `gcloud` or using `brew` et.al.

```
$ gcloud config list project
$ gcloud config set compute/zone europe-west1-b
$ gcloud config set container/use_client_certificate False

$ gcloud container clusters create data-services --num-nodes=5 --enable-autoscaling --min-nodes=5 --max-nodes=7

$ gcloud container clusters describe data-services

$ gcloud auth application-default login
$ kubectl cluster-info
```

Once you are done, remember to delete the cluster again!
```
$ gcloud container clusters delete data-services
```

## Administration

- **Rabbit MQ**
  - URL: http://localhost:15672
  - User: guest
  - PWD: guest
- **Active MQ**
  - URL: http://localhost:8161
  - User: admin
  - PWD: admin
- **Cockroach DB**
  - URL: http://localhost:8080
  - User: root
  - PWD: root

## Cockroach DB Setup

Follow the instructions here: https://www.cockroachlabs.com/docs/stable/orchestrate-cockroachdb-with-kubernetes-insecure.html

Also, I have included the YAML files in `kubernetes/infrastructure/`. First apply the deployment.
Once everything has started, run the init job and finally create the services.


Test the cluster:

```
$ kubectl run cockroachdb -it --image=cockroachdb/cockroach --rm --restart=Never -- sql --insecure --host=cockroachdb-public
```

## REST endpoints

To test the different REST endpoints use the Postman collection: `data-services.postman_collection.json`

# References

- https://speakerdeck.com/lreimer/data-services-processing-big-data-the-microservice-way
- https://docs.payara.fish/documentation/payara-micro/payara-micro.html
- http://blog.payara.fish/kubernetes-native-discovery-with-payara-micro
- https://github.com/MeroRai/payara-hazelcast-kubernetes
- https://github.com/hazelcast/hazelcast-kubernetes
- https://hub.docker.com/r/hazelcast/hazelcast-kubernetes/
- http://blog.payara.fish/payara-micro-jca-adapters-mqtt
- http://blog.payara.fish/payara-micro-jca-adapters-apache-kafka
- http://blog.payara.fish/cloud-connectors-in-payara-micro

# Maintainer

M.-Leander Reimer (@lreimer), <mario-leander.reimer@qaware.de>

# License

This software is provided under the MIT open source license, read the `LICENSE`
file for details.
