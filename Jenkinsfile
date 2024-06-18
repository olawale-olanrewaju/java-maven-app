#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'MAVEN'
    }
    stages {
        stage('build jar') {
            steps {
                script {
                    echo "Building the application..."
                    sh "mvn package"
                }
            }
        }
        stage('build image') {
                    steps {
                        script {
                            echo "Building the Docker image..."
                            withCredentials([usernamePassword(credentialsId: 'dockerhub-login', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME') ]) {
                                sh 'docker build -t laweee/demo-java-maven-app:1.2 .'
                                sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
//                                 sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 058264545261.dkr.ecr.us-east-1.amazonaws.com"
                                sh 'docker push laweee/demo-java-maven-app:1.2'
                            }
                        }
                    }
                }
        stage('deploy') {
            steps {
                script {
                    echo "Deploying the application..."
                }
            }
        }
    }
}
