FROM openjdk:21
WORKDIR /app
COPY ./target/fct_bbdd-0.0.1-SNAPSHOT.jar /app
COPY .env .env
EXPOSE 4242
CMD ["java", "-jar", "fct_bbdd-0.0.1-SNAPSHOT.jar"]