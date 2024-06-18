#!/usr/bin/env groovy

def gv

pipeline {
    agent any
    tools {
        maven 'MAVEN'
    }
    stages {
        stage('Init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage('Build Jar') {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage('Build and Publish Image') {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage('Deploy App') {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
