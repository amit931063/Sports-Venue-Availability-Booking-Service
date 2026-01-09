# Use a lightweight JRE for the runtime
FROM eclipse-temurin:17-jre-alpine

# Create a non-root user for security (Production Standard)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Define the location of the jar file
ARG JAR_FILE=target/*.jar

# Copy the jar from your target folder to the container
COPY ${JAR_FILE} app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]