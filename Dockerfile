FROM eclipse-temurin:21-jre-jammy

WORKDIR /resortHUB-backend-0.0.1-SNAPSHOT

COPY target/*.jar resortHUB-backend-0.0.1-SNAPSHOT.jar

EXPOSE 3000

ENTRYPOINT ["java","-jar","resortHUB-backend-0.0.1-SNAPSHOT.jar"]