FROM bellsoft/liberica-openjdk-alpine-musl:17
# Set the working directory in the container
WORKDIR /app
# Copy the Maven pom.xml file to download dependencies
COPY pom.xml .
# Copy the source code
COPY src src
# Copy Jar
COPY /target/*.jar /app/app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]