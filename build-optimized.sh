#!/bin/bash

# Build script with performance optimizations

echo "Building phishing-detector with performance optimizations..."

# Set Maven options for faster builds
export MAVEN_OPTS="-Xmx2048m -XX:+UseG1GC"

# Clean and build with optimizations
./mvnw clean package -DskipTests=false \
  -Dmaven.compiler.optimize=true \
  -Dmaven.compiler.debug=false \
  -Dmaven.test.skip=false

# Build Docker image
echo "Building optimized Docker image..."
docker build -t phishing-detector:optimized .

echo "Build completed successfully!"
echo "To run the optimized container:"
echo "docker run -d -p 8080:8080 --name phishing-detector-opt phishing-detector:optimized"