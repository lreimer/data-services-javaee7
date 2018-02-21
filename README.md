# Data services: Processing big data the microservice way with Java EE 7

This repository contains the showcase for my talk at O'Reilly Architecture Conference NY 2018.
For details see: https://conferences.oreilly.com/software-architecture/sa-ny/public/schedule/detail/63967

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
$ gcloud config set container/use_client_certificate True

$ gcloud container clusters create data-services --password=12qwaszx --num-nodes=5 --enable-autoscaling --min-nodes=5 --max-nodes=7 --addons=HttpLoadBalancing,HorizontalPodAutoscaling,KubernetesDashboard --scopes=gke-default,storage-rw

$ gcloud container clusters describe data-services

$ gcloud auth application-default login
$ kubectl cluster-info
```

Once you are done, remember to delete the cluster again!
```
$ gcloud container clusters delete data-services
```


# References

- http://blog.payara.fish/kubernetes-native-discovery-with-payara-micro

# Maintainer

M.-Leander Reimer (@lreimer), <mario-leander.reimer@qaware.de>

# License

This software is provided under the MIT open source license, read the `LICENSE`
file for details.
