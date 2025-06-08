#!/bin/sh

# Stop and remove Docker containers if they exist

echo "Stopping and removing Docker containers..."

if docker ps -q -f name=web-frontend-docker > /dev/null; then
  echo "Stopping web-frontend-docker..."
  docker stop web-frontend-docker
  docker rm web-frontend-docker
else
  echo "web-frontend-docker is not running."
fi

if docker ps -q -f name=api-server-docker > /dev/null; then
  echo "Stopping api-server-docker..."
  docker stop api-server-docker
  docker rm api-server-docker
else
  echo "api-server-docker is not running."
fi

# Stop nginx if it's running via Homebrew
echo "Stopping nginx (via Homebrew)..."
brew services stop nginx

# Uncomment the following line if you want to stop Colima as well

# echo "Stopping Colima..."
# colima stop

echo "Cleanup complete."