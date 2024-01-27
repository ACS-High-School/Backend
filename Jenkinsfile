pipeline {
    agent any
    environment {
        DOCKER_REPO_URI = "853963783084.dkr.ecr.ap-northeast-2.amazonaws.com/backend"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        AWS_CREDENTIALS_ID = "AWS_ECR"
        PREVIOUS_IMAGE_TAG = "${env.BUILD_NUMBER.toInteger() - 1}" // 이전 이미지의 태그를 현재 태그에서 1을 빼서 설정합니다.
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_REPO_URI}:${IMAGE_TAG} ."
                }
            }
        }
        stage('Push to ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin ${DOCKER_REPO_URI}"
                    sh "docker push ${DOCKER_REPO_URI}:${IMAGE_TAG}"
                }
            }
        }
        stage('Delete Previous Image') {
            steps {
                script {
                    // 이전 이미지 삭제
                    sh "docker rmi ${DOCKER_REPO_URI}:${PREVIOUS_IMAGE_TAG}"
                }
            }
        }
    }
}
