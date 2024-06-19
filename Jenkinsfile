#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'MAVEN'
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
                        sh 'docker build -t laweee/demo-java-maven-app:1.2 .'
                        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
                        sh 'docker push laweee/demo-java-maven-app:1.2'
                    }
                }
            }
        }
        stage('Deploy App') {
            steps {
                script {
                    echo "Deploying the application..."
                    def dockerCmd = 'docker run -d -p 8080:8080 laweee/demo-java-maven-app:1.2'
                    sshagent(['ec2-ssh-key-global']) {
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@3.82.175.76 ${dockerCmd}"
                    }
                }
            }
        }
    }
}
