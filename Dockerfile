# Build stage: Using Eclipse Temurin JDK 17 on Alpine Linux platform for building the application
FROM --platform=linux/x86_64 eclipse-temurin:17-jdk-alpine as build

# Set the working directory inside the container
WORKDIR /workspace/app

# Copy build configuration files
COPY build.gradle .
COPY gradlew .
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/
# Copy the source code of the application
COPY src src

# Grant execute permissions to the Gradle wrapper script
RUN chmod +x ./gradlew
# Run the Gradle build to clean and assemble the project
RUN ./gradlew clean assemble
# Create a directory for extracted contents of the Spring Boot jar
RUN mkdir -p target/extracted
# Extract the contents of the Spring Boot jar using layertools
RUN java -Djarmode=layertools -jar ./build/libs/*.jar extract --destination target/extracted

# Startup stage: Using Eclipse Temurin JDK 17 on Alpine Linux platform for running the application
FROM --platform=linux/x86_64 eclipse-temurin:17-jdk-alpine as startUp

# Define a volume for temporary files (optional, can be used for caching, etc.)
VOLUME /tmp

# Define an argument for the path to the extracted contents
ARG EXTRACTED=/workspace/app/target/extracted

# Copy the extracted dependencies, spring-boot-loader, snapshot dependencies, and application files from the build stage
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./

# Set the entry point to run the application using Spring Boot's JarLauncher
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]
