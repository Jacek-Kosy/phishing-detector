FROM openjdk:17-jdk-slim

# Set JVM options for optimal performance
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+OptimizeStringConcat -Djava.security.egd=file:/dev/./urandom"

# Create app user for security
RUN useradd -r -s /bin/false appuser

WORKDIR /app

# Copy the jar file
COPY target/phishing-detector-*.jar app.jar

# Change ownership to app user
RUN chown appuser:appuser app.jar

USER appuser

EXPOSE 8080

# Use exec form to ensure proper signal handling
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]