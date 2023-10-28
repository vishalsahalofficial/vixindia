FROM maven:3.8.4-openjdk-11

WORKDIR /app

COPY . /app

RUN mvn clean install

WORKDIR /app

COPY --from=0 /app/target/*.jar /app/vixindia.jar

EXPOSE 8080

CMD ["java", "-jar", "vixindia.jar"]