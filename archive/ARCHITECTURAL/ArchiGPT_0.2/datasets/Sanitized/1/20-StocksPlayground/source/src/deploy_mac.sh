#!/bin/zsh

# Start the Docker Compose stack in detached mode
osascript -e 'tell app "Terminal"
    do script "docker-compose -f '/Users/elenabianchini/Documents/Magistrale/LAPProject/docker-compose.yml' up"
end tell' &

echo "Remember to execute docker-compose down and docker volume prune when you are done."
sleep 1

# Wait for the containers to start up
echo "Waiting for containers to start up..."
sleep 60

# Get the container ID for the container with 'mongo1' in its name
MONGO1_CONTAINER_ID=$(docker container ls --all | grep mongo1 | awk '{print $1}')

# Execute the rs-init.sh script in the 'mongo1' container
echo "Executing rs-init.sh in mongo1 container..."
docker exec -it $MONGO1_CONTAINER_ID /scripts/rs-init.sh
