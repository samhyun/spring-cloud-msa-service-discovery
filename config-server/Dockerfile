#stage 1
#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

#unpackage jar file
RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf /*.jar)

#stage 2
#Same Java runtime
FROM openjdk:11-slim

#Add volume pointing to /tmp
VOLUME /tmp

#Copy unpackaged application to new container
ARG DEPENDENCY=/build/libs/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

#execute the application
ENTRYPOINT ["java","-cp","app:app/lib/*","com.illuminarean.configserver.ConfigServerApplication"]