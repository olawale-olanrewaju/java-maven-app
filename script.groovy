def buildJar() {
    echo "building the application..."
    sh 'mvn clean package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'dockerhub-login', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh "docker build -t laweee/demo-java-maven-app:$IMAGE_VERSION ."
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh "docker push laweee/demo-java-maven-app:$IMAGE_VERSION"
    }
} 

def deployApp() {
    echo 'Deploying application to EC2 instance'
} 

return this