# Video streaming service

## Project notes

A bunch of things may need explanation. 
1. What is com.mykola.example.client.storage.StorageServiceClientProxy? 
I suppose that video content in similar real services is stored separately in special storages, 
so I add it to simulate such storage calls.
2. Sorry for the lack of tests. I just didn't have enough time, as I decided to focus on doing one thing more or less well in such a short period of time, so I devoted more time to implementation.

I will be happy to answer any remaining questions during the conversation)

## Environment variables

[//]: # (todo) 
tbd: extract db creds to env vars

## Local Project Build

```gradlew video-streaming-service:clean video-streaming-service:build```

## Local Project Run

Just run with default profile

[//]: # (todo)
tbd: add profiles (local, it, etc.)

## Local Url

http://localhost:8763

## Local swagger Url

http://localhost:8763/swagger-ui/index.html#/

## Local db setup:

It is H2 db for test and runtime at the moment. No need for additional setups.

## Local IT test Run
tbd

