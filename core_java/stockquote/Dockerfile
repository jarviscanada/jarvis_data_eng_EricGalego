FROM postgres:latest

RUN apt-get update -y
RUN apt-get install -y default-jre-headless

COPY scripts/initDb.sql /docker-entrypoint-initdb.d/
COPY scripts/startup.sh /usr/local/bin/startup.sh
COPY src/main/resources/ /usr/local/app/stockquote/src/main/resources/
COPY target/stockquote*shaded.jar /usr/local/app/stockquote/stockquote.jar

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=password

WORKDIR "/usr/local/app/stockquote"
EXPOSE 5432

ENTRYPOINT ["/usr/local/bin/startup.sh"]