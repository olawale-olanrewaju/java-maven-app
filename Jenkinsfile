#!/usr/bin/env groovy

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
        stage('Increment version') {
            steps {
                script {
                    echo 'Incrementing application version'
                    sh 'mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_VERSION = "$version-$BUILD_NUMBER"
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
//             when {
//                 expression {
//                     BRANCH_NAME == 'master'
//                 }
//             }
            steps {
                script {
                    gv.buildImage()
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

        stage('Commit version update') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-login', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                        sh 'git config --global user.email "jenkins@email.com"'
                        sh 'git config --global user.name "jenkins"'

                        sh 'git status'
                        sh 'git branch'
                        sh 'git config --list'

                        sh "git remote set-url origin https://${USERNAME}:${PASSWORD}@github.com/olawale-olanrewaju/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "Bumping up versions"'
                        sh 'git push origin HEAD:jenkins-jobs'
                    }
                }
            }
        }
    }
}
