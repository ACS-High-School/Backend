pipeline {
    agent any

    environment {
        REGION = 'ap-northeast-2'
        ECR_PATH = '853963783084.dkr.ecr.ap-northeast-2.amazonaws.com'
        ECR_IMAGE = 'backend'
        AWS_CREDENTIAL_ID = 'AWS_ECR'
    }

    stages {
        stage('Clone Repository') {
            steps {
                slackSend(channel: "#jenkins", color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                checkout scm
            }
        }

        stage('Add Env') {
            steps {
                withCredentials([file(credentialsId: 'application_properties', variable: 'application')]) {
                    // src/main/resources 폴더가 없으면 생성
                    sh 'mkdir -p src/main/resources'
                    sh 'chmod 755 src/main/resources'
                    sh 'cp $application  src/main/resources/application.properties'
                }
            }
        }

        stage('Build with Gradle') {
            steps {
                sh './gradlew clean build'
            }
        }
        
        stage('Docker Build') {
            steps {
                script {
                    docker.withRegistry("https://${ECR_PATH}", "ecr:${REGION}:${AWS_CREDENTIAL_ID}") {
                        image = docker.build("${ECR_IMAGE}")
                    }
                }
            }
        }

        stage('Push to ECR') {
            steps {
                script {
                    docker.withRegistry("https://${ECR_PATH}", "ecr:${REGION}:${AWS_CREDENTIAL_ID}") {
                        image.push("${env.BUILD_NUMBER}")
                        image.push("latest")
                    }
                }
            }
        }

        stage('CleanUp Images') {
            steps {
                sh "docker rmi ${ECR_PATH}/${ECR_IMAGE}:${env.BUILD_NUMBER}"
                sh "docker rmi ${ECR_PATH}/${ECR_IMAGE}:latest"
                sh "docker rmi ${ECR_IMAGE}:latest"
            }
        }
        stage('Clean Workspace') {
            steps {
                sh "docker system prune -af"
                cleanWs()
            }
        }
    }
    post {
        failure {
            echo 'file update failure'
            slackSend(channel: "#jenkins", color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        success {
            slackSend(channel: "#jenkins", color: '#00FF00', message: "MANIFEST UPDATE SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
            echo 'file update success'
        }
    }
}
