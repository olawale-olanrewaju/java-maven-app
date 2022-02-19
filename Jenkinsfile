#!/usr/bin/env groovy

def gv

pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("build jar") {
            steps {
                script {
                    echo "Building the JAR"
                }
            }
        }
        stage("build and push image") {
            steps {
                script {
                    echo "Building the IMAGE"
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "DEPLOYING the image"
                }
            }
        }
    }
}
