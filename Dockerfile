FROM 192.168.18.247:8888/library/amazoncorretto:17-alpine-jdk


ENV TZ=Asia/Shanghai
ENV LANG C.UTF-8
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /catalog-service

WORKDIR /catalog-service

EXPOSE 8080

ADD ./build/libs/catalog-service-0.0.1-SNAPSHOT.jar ./app.jar

CMD java $JAVA_OPTS -jar app.jar