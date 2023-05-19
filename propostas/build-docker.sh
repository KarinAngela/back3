#! /bin/sh

# Build JAR
./mvnw package

# Build container
docker build -f src/main/docker/Dockerfile.jvm -t kariangela/propostas-quarkus-jvm .

