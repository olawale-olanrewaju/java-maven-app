#!/usr/bin/env groovy

// @Library('jenkins-shared-library')_

@Library('jenkins-shared-library')
def gv

pipeline {
    agent any
    tools {
        maven 'MAVEN'
    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage('Build Jar') {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage('Build and Publish Image') {
//             when {
//                 expression {
//                     BRANCH_NAME == 'master'
//                 }
//             }
            steps {
                script {
                    buildImage "laweee/demo-java-maven-app:1.3"
                    dockerLogin()
                    dockerPush "laweee/demo-java-maven-app:1.3"
                }
            }
        }
        stage('Deploy App') {
//             when {
//                 expression {
//                     BRANCH_NAME == 'master'
//                 }
//             }
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
