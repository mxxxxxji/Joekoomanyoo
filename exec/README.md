# 빌드 및 배포

## 1. 프로젝트 사용 도구
- 이슈 관리  : JIRA
- 형상 관리 : Gitlab
- 커뮤니케이션 : Notion, Mattermost, discord
- 디자인 : Figma, Zeplin, adobe
- UCC : 모바비
- CI/CD : Jenkins
<br />

## 2. 개발 환경
**Android**
* 언어
    - Kotlin 1.7.0
- IDE
    - AndroidStudio
<br />

**BackEnd**
- 언어
    - Java 11
- 프레임워크
    - Spring Boot 2.6.3
    - Gradle 7.4.X
    - Querydsl 5.0.0
- 인터페이스
    - JPA
- IDE
    - IntelliJ IDEA 2022.1.4
<br />

**DataBase** <br />
    - MySQL 8.0.29 <br />
<br />

## 3. 외부 서비스
- Google Cloud Storage
- Firebase Realtime DB
- KakaoMap API
- KakaoLogin API
<br />

## 4. 빌드
**1. 환경 변수**
```bash
    .application.yml

    spring:
    # mysql DB
    datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: mysqlDB 주소
        username: 유저 이름
        password: 유저 비밀번호

    # jpa
    jpa:
        database:mysql
    show-sql: true
    # jpa나 hibernate를 통해 CRUD를 실행하면 해당 CRUD의 sql을 로깅으로 보여준다
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    #각기 다른 DB에 맞는 SQL문법을 처리
    hibernate:
        ddl-auto: none
    #서버를 실행할 때 마다 데이터 베이스 초기화 전략 : none (아무것도 설정 x ,초기값 )
    properties:
        hibernate:
            format_sql: true
                    #로깅에 표시되는 sql을 보기 좋게 해준다

    # mail
    mail:
        host: smtp.gmail.com
        port: 587
        username: 유저 gmail 아이디
    password: 앱 사용 비밀번호
    properties:
        mail.smtp.auth: true
        mail.smtp.starttls.enable: true
```
<br />

**2. 배포** <br />
* **nginx** <br />
```bash
    sudo apt get install nginx
```
<br />

* **nginx** <br />
```bash
ubuntu@ip-172-26-10-27:/etc/nginx/sites-enabled$ sudo vi default
            proxy_pass http://localhost:8081;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header Host $http_host;
            proxy_set_header X-NginX-Proxy true;
    }
location /stomp/chat {
            proxy_pass http://localhost:8081/stomp/chat;
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection "upgrade";
            proxy_set_header Host $host;
    }

    location /api {
            proxy_pass http://localhost:8081/api;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header Host $http_host;
            proxy_set_header X-NginX-Proxy true;
        }

        location /image {
                proxy_pass http://localhost:8082/image;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
                proxy_set_header X-NginX-Proxy true;
        }
    }
```
<br />

* **Docker, Docker-Compose 설치** <br />
```bash
        sudo apt get install docker 
        sudo apt get install docker-compose
```
<br />

* **API 서버 배포** <br />
```bash
    https://lab.ssafy.com/s07-webmobile4-sub2/S07P12D102.git
    docker build -t project
    docker run -d --name server -p 8081:8080 project:latest
 ```
<br />

* **파일 서버, DB 서버 배포** <br />
```bash
    git clone https://github.com/Jaehwany/File-Server.git
    docker-compose up -d
```
<br />
