pipeline {
    agent any

    environment {
        REGION = 'ap-northeast-2'
        ECR_PATH = '853963783084.dkr.ecr.ap-northeast-2.amazonaws.com'
        ECR_IMAGE = 'backend'
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        DEPLOYMENT_FILE = "deployment.yml" // 파일 경로 수정
        AWS_CREDENTIAL_ID = 'AWS_ECR'
    }

    stages {
        stage('Clone Repository') {
            steps {
                slackSend(channel: "#jenkins-argo", color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                checkout scm
            }
        }

        stage('Add Env') {
            steps {
                withCredentials([file(credentialsId: 'application_properties', variable: 'application')]) {
                    // src/main/resources 폴더가 없으면 생성
                    sh 'sudo mkdir -p src/main/resources'
                    sh 'sudo cp $application  src/main/resources/application.properties'
                    sh 'sudo chmod 664 src/main/resources/application.properties'
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

        stage('Update Deployment File') {
            steps {                
                // GitHub 토큰을 사용하여 인증
                withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                    script {
                        // 다른 저장소로 전환
                        sh "git clone https://${GITHUB_TOKEN}@github.com/ACS-High-School/Eks.git other-repo"
                        sh "cd other-repo"
                        
                        dir('other-repo/was') {
                            // 로컬 저장소 설정에 사용자 이름과 이메일 추가
                            sh "git config user.email 'ejhjjp11@naver.com'"
                            sh "git config user.name 'hjp1016'"
                        
                            // deployment.yaml 파일에서 이미지 태그를 업데이트합니다.
                            sh "sed -i 's|${ECR_PATH}/${ECR_IMAGE}:[^ ]*|${ECR_PATH}/${ECR_IMAGE}:${IMAGE_TAG}|' ${DEPLOYMENT_FILE}"

            
                            // 변경 사항을 git에 커밋하고 푸시합니다.
                            sh "git add ${DEPLOYMENT_FILE}"
                            sh "git commit -m 'Update the backend image tag to ${IMAGE_TAG}'"
                            sh "git push"
                        }
                    }
                }
            }
        }
    }

    post {
        failure {
            echo 'file update failure'
            slackSend(channel: "#jenkins-argo", color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        success {
            slackSend(channel: "#jenkins-argo", color: '#00FF00', message: "MANIFEST UPDATE SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
            echo 'file update success'
        }
    }
}
