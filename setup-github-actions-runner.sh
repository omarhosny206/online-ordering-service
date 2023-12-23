#!/bin/bash

# Set your GitHub repository, username, personal access token and runner name
GITHUB_REPOSITORY="<your-github-repository>"
GITHUB_USERNAME="<your-github-username>"
GITHUB_PERSONAL_ACCESS_TOKEN="<your-personal-access-token>"
RUNNER_NAME="<runner-name>"

# Docker run command to create and start the GitHub Actions runner container
docker run -d --name $RUNNER_NAME \
    -e GITHUB_USERNAME=$GITHUB_USERNAME \
    -e GITHUB_REPOSITORY=$GITHUB_REPOSITORY \
    -e GITHUB_PERSONAL_ACCESS_TOKEN=$GITHUB_PERSONAL_ACCESS_TOKEN \
    -e RUNNER_NAME=$RUNNER_NAME \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v /usr/bin/docker:/usr/bin/docker \
    -v /usr/local/bin/docker-compose:/usr/local/bin/docker-compose \
    omarhosny102/github-runner:latest
