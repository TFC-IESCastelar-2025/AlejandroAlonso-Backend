FROM openjdk:23
WORKDIR /app
COPY ./fct_bbdd-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "fct_bbdd-0.0.1-SNAPSHOT.jar"]