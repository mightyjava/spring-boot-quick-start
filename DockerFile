FROM openjdk:8-jdk-alpine
ADD target/spring-boot-quick-start.jar spring-boot-quick-start.jar
ENTRYPOINT ["java","-jar","spring-boot-quick-start.jar"]