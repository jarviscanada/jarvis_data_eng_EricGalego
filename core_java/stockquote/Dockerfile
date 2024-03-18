FROM openjdk:8-alpine
COPY target/stockquote*shaded.jar /usr/local/app/stockquote/stockquote.jar
COPY src/main/resources/properties.txt /usr/local/app/stockquote/src/main/resources/properties.txt
EXPOSE 5432
WORKDIR "/usr/local/app/stockquote"

ENTRYPOINT ["java","-jar","/usr/local/app/stockquote/stockquote.jar"]