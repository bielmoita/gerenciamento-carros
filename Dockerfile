FROM openjdk:11
WORKDIR /app
COPY target/Gerenciamento-de-Carros-0.0.1.jar /app/Gerenciamento-de-Carros-0.0.1.jar
EXPOSE 8080
CMD ["java", "-jar", "Gerenciamento-de-Carros-0.0.1.jar"]