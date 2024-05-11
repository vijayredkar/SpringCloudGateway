FROM bellsoft/liberica-openjdk-alpine-musl:17
# Set the working directory in the container
WORKDIR /app
# Copy the Maven pom.xml file to download dependencies
COPY pom.xml .
# Copy the source code
COPY src src
# Copy the configured attributes json
COPY scg-customers-payments-view.json /app/scg-customers-payments-view.json
# Copy the configured properties file
COPY sensitive-data.properties /app/sensitive-data.properties
# Copy Jar
COPY /target/*.jar /app/app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]