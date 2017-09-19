FROM openjdk:8-jdk as builder
COPY . /project
WORKDIR /project
RUN ./gradlew build -x test

FROM openjdk:8-jre-alpine
COPY --from=builder /project/build/libs/*.jar /plugin-vimdriver-test.jar
RUN mkdir -p /var/log/openbaton
ENV RABBITMQ localhost
ENV RABBITMQ_PORT 5672
ENV CONSUMERS 3
ENTRYPOINT ["sh", "-c", "java -jar /plugin-vimdriver-test.jar test $RABBITMQ $RABBITMQ_PORT $CONSUMERS"]
