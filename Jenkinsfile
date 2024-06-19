#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'MAVEN'
    }
    environment {
        IMAGE_NAME = 'laweee/demo-java-maven-app:1.3'
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
                    def ec2Instance = 'ubuntu@3.82.175.76'
                    sshagent(['ec2-ssh-key-global']) {
                        sh "scp server-cmds.sh $ec2Instance:/home/ubuntu"
                        sh "scp docker-compose.yaml $ec2Instance:/home/ubuntu"
                        sh "ssh -o StrictHostKeyChecking=no $ec2Instance $shellCmd"
                    }
                }
            }
        }
    }
}
