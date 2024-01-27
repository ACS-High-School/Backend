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
                checkout scm
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    docker.withRegistry("https://${ECR_PATH}", "ecr:${REGION}:${AWS_CREDENTIAL_ID}") {
                        def customImage = docker.build("${ECR_IMAGE}:${env.BUILD_NUMBER}")
                    }
                }
            }
        }
        stage('Push to ECR') {
            steps {
                script {
                    docker.withRegistry("https://${ECR_PATH}", "ecr:${REGION}:${AWS_CREDENTIAL_ID}") {
                        customImage.push()
                        customImage.push("latest")
                    }
                }
            }
        }
        stage('CleanUp Images') {
            steps {
                sh"""
                docker rmi ${ECR_PATH}/${ECR_IMAGE}:${env.BUILD_NUMBER}
                docker rmi ${ECR_PATH}/${ECR_IMAGE}:latest
                """
            }
        }
    }
}
