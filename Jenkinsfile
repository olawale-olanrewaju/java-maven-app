#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage('Build App') {
            steps {
                script {
                    echo "Building the app..."
                }
            }
        }
        stage('Build Image') {
            steps {
                script {
                    echo "Building the image..."
                }
            }
        }
        stage('Deploy App') {
            environment {
                AWS_ACCESS_KEY_ID = credentials('jenkins-aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('jenkins-aws_secret_access_key')
            }
            steps {
                script {
                    echo "Deploying the application..."
                    sh "kubectl create deployment nginx-deployment --image=nginx"
                }
            }
        }
    }
}
