# Use an official Node base image
FROM node:lts

# Install Smee client
RUN npm install --global smee-client

# Expose any necessary ports (not required for Smee client but shown here for completeness)
# EXPOSE 3000

# Command to run Smee client
ENTRYPOINT ["smee"]