# Use the official Jenkins base image
FROM jenkins/jenkins:latest
USER root
# Install Packages
RUN apt update -y
RUN apt install -y curl
RUN apt install -y npm
RUN apt install -y python3
RUN apt install -y pip
RUN apt install -y jq
# Install Docker
RUN apt update -y
RUN curl -sSL https://get.docker.com/ | sh
# Install Docker Compose
RUN apt install -y docker-compose
USER jenkins
