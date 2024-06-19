def buildJar() {
    echo "Building the application..."
    sh "mvn package"
}

def buildImage() {
    echo "Building the Docker image..."
    withCredentials([usernamePassword(credentialsId: 'dockerhub-login', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME') ]) {
        sh 'docker build -t laweee/demo-java-maven-app:1.2 .'
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
//      sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 058264545261.dkr.ecr.us-east-1.amazonaws.com"
        sh 'docker push laweee/demo-java-maven-app:1.2'
    }
}

def deployApp() {
    echo "Deploying the application..."
    def dockerCmd = 'docker run -d -p 3000:80 laweee/nodejs-react-app:1.0'
    sshagent(['ec2-ssh-server-key']) {
        sh "ssh -o StrictHostKeyChecking=no ubuntu@3.82.175.76 ${dockerCmd}"
    }
}

return this