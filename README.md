# Backend

- 연합학습 AI 플랫폼 서비스를 위한 API 구현
- API 구현은 다음과 같이 나눌 수 있다.
  - Token Verify
  - Inference
  - Group
  - Federated Learining ( *FL* )
  - S3

<br>

## Features
### Token Verify
- 인증을 거친 사용자의 인가 처리
  - AWS Cognito 에서 발급 받은 토큰을 Spring Security 를 통해
    모든 API 요청 시 토큰 유효성 검증 수행
  
### Inference
- Inference 서비스 이용을 위한 API 요청 처리
  - Inference 이용에 필요한 입력 폼을 Inferenec 테이블에 **`POST` inference/upload**
  - 서비스 이용 후 결과 확인을 위한 **`GET` inference/result**


### Group
- FL 서비스 이용을 위한 그룹 관련 요청 처리
  - 그룹 생성을 위한 **`POST` group/create**
  - 그룹 참여를 위한 **`POST` group/join**
  - 그룹원을 얻어내기 위한 **`GET` group/users**
- 연합 학습을 위한 AWS API Gateway 에 API 요청
  - 연합 학습 시작을 위한 **`POST` *apiurl*/execute** (AWS API Gateway 에 요청)
  - 그룹원들 개인에게 주어지는 LAB 환경을 위한 **`GET` *apiurl*/space_name/{id}** (AWS API Gateway 에 요청)

### Federated Learining
- 연합 학습 진행 후 결과 확인을 위한  **`GET` fl/results**

<br>

 
## Technologies

- [JDK](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html) corretto-17
- [Spring Boot](https://spring.io/blog/2024/04/18/spring-boot-3-2-5-available-now) 3.2.5
- [Gradle](https://docs.gradle.org/8.5/release-notes.html) 6.0.16
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Security](https://spring.io/projects/spring-security)
- [MySQL](https://dev.mysql.com/doc/relnotes/mysql/8.0/en/news-8-0-35.html) 8.0.35
- [AWS SDK](https://docs.aws.amazon.com/ko_kr/sdk-for-java/latest/developer-guide/home.html) V2

<br>

## Prerequisites

### S3 버킷 생성
Inference 의 입력, 출력 결과 파일과 FL 결과 파일 저장을 위한 S3 버킷 생성 필요

### AWS Lambda와 AWS API Gateway 설정
FL Workflow 에 해당하는 AWS Step Function 시작 요청인 [StartExecution](https://docs.aws.amazon.com/step-functions/latest/apireference/API_StartExecution.html) API 요청을 위한 Lambda 와 API Gateway 설정 필요
  
위의 작업들을 통해 프로젝트 내부에 `aws-exports.js` 생성 확인 후 진행


## Configuration
- `application.properties`에 API 정보 입력
  - AWS 계정 정보 (Access Key, Secret Key)
  - AWS Cognito JWT 토큰 검증 url
  - AWS 서비스 정보
    - S3 / RDS / API Gateway / dynamoDB / Step Function
  - CORS 설정


<br>

## Trouble Shooting 
### 사용자 인가를 위한 토큰 설정
- 문제 원인
  - 인가 처리 방식에 대한 설계 구현
  - 프론트 서버와 백엔드 서버 간의 CORS 에러
  - 쿠키 설정 시 도메인 설정 에러 발생

- 해결 방안
  - 클라이언트의 쿠키를 통한 토큰 저장과 백엔드 서버에서의 유효성 검증 처리
  - Spring Security 의 CORS 설정 추가
  - 서버에 맞는 도메인으로 쿠키 설정

  
<br>

## Feature improvements
### Inference 결과 상태 체크  
- 개선 이유
  - 3초마다 API를 통해 DB의 status 값 체크
  - Inference 작업이 오래걸릴 경우 완료될 때까지 status 값을 계속 체크해야함

  
- 개선 방안
  - Lambda 트리거를 API GateWay로 수정하여 로직을 처리
  - Lambda의 작업이 완료되면 Response 반환 방식 적용


<br>


## Contribution
- 🫠 [김선우](https://github.com/sw801733)
- 🫢 [홍준표](https://github.com/hjp1016)
