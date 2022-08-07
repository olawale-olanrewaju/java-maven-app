#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'maven-3.8.6'
    }
    stages {
        stage('build jar') {
            steps {
                script {
                    echo "building the application..."
                    sh 'mvn package'
                }
            }
        }
        stage('build image') {
            steps {
                script {
                    echo "building the docker image..."
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', passwordVariable: 'PASS', usernameVariable: 'USER')])
                        sh 'docker build -t wh1227/demo-app:jma-2.0 .'
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                        sh 'docker push wh1227/demo-app:jma-2.0'
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

