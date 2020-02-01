#
# Build stage
#
FROM gradle:5.6.2-jdk11 as compiler

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

# copy source code
COPY build.gradle.kts settings.gradle.kts gradlew $APP_HOME
COPY gradle gradle
COPY src src

# create application jar
RUN ./gradlew clean build -x test --stacktrace

# move application jar
RUN mv ./build/libs/auth-service-0.0.1-SNAPSHOT.jar authservice.jar
#
# Run stage
#
FROM adoptopenjdk/openjdk11:jre-11.0.4_11-alpine

ENV APP_HOME=/usr/app/
ENV MAX_RAM_PERCENTAGE="-XX:MaxRAMPercentage=70"
ENV MIN_RAM_PERCENTAGE="-XX:MinRAMPercentage=70"

COPY --from=compiler $APP_HOME/authservice.jar $APP_HOME/authservice.jar

WORKDIR $APP_HOME

ENV JAVA_OPTS="$SECURITY_OPTS $MAX_RAM_PERCENTAGE $MIN_RAM_PERCENTAGE"
ENTRYPOINT exec java $JAVA_OPTS -jar authservice.jar
EXPOSE 8080