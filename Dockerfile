# Amazon Corretto 17을 베이스 이미지로 사용
FROM amazoncorretto:17

# 포트 8080 노출
EXPOSE 8080

# 앱의 jar 파일 추가
COPY build/libs/b3o-0.0.1-SNAPSHOT.jar app.jar

# jar 파일 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
