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
            steps {
                script {
                    echo "Deploying the application..."
                    withKubeCredentials(kubectlCredentials: [credentialsId: 'linode-k8s-cluster', serverUrl: 'https://d00de7a4-ea02-430f-a746-06c5c19518f4.eu-west-1.linodelke.net:443']) {
                        sh "kubectl create deployment nginx-deployment --image=nginx"
                    }
                }
            }
        }
    }
}
