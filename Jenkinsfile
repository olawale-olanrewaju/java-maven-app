#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'MAVEN'
    }
    environment {
        IMAGE_NAME = 'laweee/demo-java-maven-app:1.2'
    }
    stages {
        stage('Build Jar') {
            steps {
                script {
                    echo "Building the application..."
                    sh "mvn clean package"
                }
            }
        }
        stage('Build and Publish Image') {
            steps {
                script {
                    echo "Building the Docker image..."
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-login', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME') ]) {
                        sh "docker build -t $IMAGE_NAME ."
                        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
                        sh "docker push $IMAGE_NAME"
                    }
                }
            }
        }
        stage('Deploy App') {
            steps {
                script {
                    echo "Deploying the application..."
                    def shellCmd = "bash ./server-cmds.sh $IMAGE_NAME"
                    sshagent(['ec2-ssh-key-global']) {
                        sh 'scp server-cmds.sh ubuntu@3.82.175.76:/home/ubuntu'
                        sh 'scp docker-compose.yaml ubuntu@3.82.175.76:/home/ubuntu'
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@3.82.175.76 ${shellCmd}"
                    }
                }
            }
        }
    }
}
