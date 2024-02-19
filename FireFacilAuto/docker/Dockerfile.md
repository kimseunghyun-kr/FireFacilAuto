## Stage 1: Build JRE
#FROM eclipse-temurin:21 as jre-build
#RUN $JAVA_HOME/bin/jlink \
#         --add-modules ALL-MODULE-PATH \
#         --strip-debug \
#         --no-man-pages \
#         --no-header-files \
#         --compress=2 \
#         --output /javaruntime
#
## Stage 2: Final Image
#FROM ubuntu:latest
#
## Install required packages in a single RUN command
#RUN apt-get update \
#    && apt-get upgrade -y \
#    && DEBIAN_FRONTEND=noninteractive apt-get install -y curl \
#    && apt-get clean && apt-get autoclean && apt-get autoremove -y \
#    && rm -rf /var/lib/apt/lists/*
#
## Set Java environment variables
#ENV JAVA_HOME=/opt/java/openjdk
#ENV PATH "${JAVA_HOME}/bin:${PATH}"
#
## Copy JRE from the build stage
#COPY --from=jre-build /javaruntime $JAVA_HOME
##COPY --from=jre-build /opt/java/openjdk $JAVA_HOME
#
## Create app directory
#RUN mkdir /opt/app
#
## Copy the JAR file to the app directory
#COPY FireFacilAuto-0.0.1-SNAPSHOT.jar /opt/app/firefacilauto.jar
#
#
## Copy entrypoint script
#COPY entrypoint.sh /entrypoint.sh
#
## Give execute permission to the entrypoint script
#RUN chmod +x /entrypoint.sh
#
## Set entry point for the application
#ENTRYPOINT ["/entrypoint.sh"]
#
## Expose ports
#EXPOSE 8080 80
#
## Add metadata to the image
#LABEL authors="user"