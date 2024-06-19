#!/usr/bin/env bash

docker compose down
docker compose up --detach
echo "Success!! Services are up."