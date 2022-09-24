# Docker를 올릴때 jdk11 버전을 사용
FROM openjdk:11-jdk
# JAR 파일의 위치를 환경변수의 형태로 선언한다.
ARG JAR_FILE=./build/libs/cowalk-0.0.1-SNAPSHOT.jar
# JAR 파일을 Docker 이미지의 파일 시스템으로 복사 (co-walk.jar로 저장)
COPY ${JAR_FILE} co-walk.jar
# 이미지를 컨테이너로 띄울 때 항상 실행되어야 하는 파일(서버 실행)을 지정
# EC2 실행 명령어: docker run -p 80:8080 -e JASYPT_PASSWORD=-Djasypt.encryptor.password={password} -e PROFILES=-Dspring.profiles.active=prod {image name}
ENTRYPOINT ["sh", "-c", "java ${JASYPT_PASSWORD} ${PROFILES} -jar /co-walk.jar"]
