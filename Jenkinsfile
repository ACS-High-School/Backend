pipeline {
    agent any
    stages {
        stage('SLACK TEST') {
            steps {
                    slackSend(
                        channel: '#jenkins', // Slack 채널 이름 설정
                        color: '#FF0000',    // 메시지 색상 설정
                        message: 'TEST'      // 보낼 메시지 내용 설정
                    )
                }
        }
    }
}

