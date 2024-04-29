FROM --platform=linux/x86_64 eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

#Copy buil file
COPY build.gradle .
#Copy the gradlew
COPY gradlew .
#Copy the Gradle wrapper scripts
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/
#Copy the project source code
COPY src src

#Asseble sources
RUN chmod +x ./gradlew
RUN ./gradlew clean assemble
#Extract jar
RUN mkdir -p target/extracted
RUN java -Djarmode=layertools -jar ./build/libs/*.jar extract --destination target/extracted

FROM --platform=linux/x86_64 eclipse-temurin:17-jdk-alpine as step2
VOLUME /tmp

ARG EXTRACTED=/workspace/app/target/extracted

COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]