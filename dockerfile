FROM eclipse-temurin:11
ENV APP_HOME = ./usr/app
WORKDIR $APP_HOME

COPY . .
RUN ["./gradlew", "--no-daemon", "clean", "build", "shadowJar"]

CMD ["java", "-jar", "./discord-bot/build/libs/discord-bot-all.jar"]