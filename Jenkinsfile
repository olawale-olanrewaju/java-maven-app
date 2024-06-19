#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'MAVEN'
    }
//     environment {
//         IMAGE_NAME = 'laweee/demo-java-maven-app:1.3'
//     }
    stages {
        stage('Increment version') {
            steps {
                script {
                    echo 'Incrementing app version'
                    sh 'mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_VERSION = "${version}"
                    env.IMAGE_NAME = "laweee/demo-java-maven-app:$IMAGE_VERSION"
                }
            }
        }
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
        stage('Commit version updated') {
            steps {
                script {
                    sshagent(['github-login-ssh']) {
                        sh "git remote set-url origin git@github.com:olawale-olanrewaju/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "Bumping up versions"'
                        sh "git push origin HEAD:$BRANCH_NAME"
                    }
                }
            }
        }
    }
}
