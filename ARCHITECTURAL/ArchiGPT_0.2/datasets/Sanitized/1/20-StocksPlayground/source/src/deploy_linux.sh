#!/bin/bash

if [[ $1 == "--build" ]]; then
  echo "Building Docker images..."
  xterm -e "docker-compose build"
fi

# Start the Docker Compose stack in detached mode
xterm -e "docker-compose up" &

echo "Remember to execute docker-compose down when you are done."
sleep 1

# Wait for the containers to start up
echo "Waiting for containers to start up..."
sleep 10

# Get the container ID for the container with 'mongo1' in its name
MONGO1_CONTAINER_ID=$(docker container ls --all | grep src_mongo1 | awk '{print $1}')
# Execute the rs-init.sh script in the 'mongo1' container
echo "Executing rs-init.sh in mongo1 container..."
docker exec -it $MONGO1_CONTAINER_ID /scripts/rs-init.sh
