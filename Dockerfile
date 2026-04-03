# Stage 1: build WAR with production Struts profile (devMode off)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -B -Pprod package -DskipTests

# Stage 2: Tomcat 9 (javax.servlet) — matches Struts 2.5
FROM tomcat:9.0-jre21-temurin
# Remove default webapps; deploy our WAR as ROOT for simpler URL (optional)
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/basic-struts.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
# App context: http://localhost:8080/ (ROOT) — WAR name basic-struts.war would be /basic-struts
# Using ROOT.war serves the app at server root.
