#!/usr/bin/env bash


export IMAGE=$1
docker compose down
docker compose up --detach
echo "Success!! Services are up."