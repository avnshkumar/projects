#!/bin/sh

# Set project root
cd ..
HOME_DIR=$(pwd)
echo "Project root: $HOME_DIR"

# Set paths
API_SERVER_DIR="$HOME_DIR/java-apps/api-server"
WEB_FRONTEND_DIR="$HOME_DIR/web-apps/testing-vite-react-ts"

# Start colima if not running
if ! colima status | grep -q "Running"; then
  echo "Starting Colima..."
  colima start
else
  echo "Colima already running."
fi

# Build backend
echo "Building backend..."
cd "$API_SERVER_DIR" || exit 1
./gradlew build -x test || exit 1

# Build backend Docker image
echo "Building backend Docker image..."
docker build -t api-server -f "$API_SERVER_DIR/api-server.Dockerfile" "$API_SERVER_DIR" || exit 1

# Run backend container
echo "Starting backend container..."
docker rm -f api-server-docker 2>/dev/null
docker run -d -p 8081:8081 --name api-server-docker api-server || exit 1

# Build frontend Docker image
echo "Building frontend Docker image..."
docker build -t web-frontend -f "$WEB_FRONTEND_DIR/testing-vite-react-ts.Dockerfile" "$WEB_FRONTEND_DIR" || exit 1

# Run frontend container
echo "Starting frontend container..."
docker rm -f web-frontend-docker 2>/dev/null
docker run -d -p 3100:3100 --name web-frontend-docker web-frontend || exit 1

echo "All services are up and running."