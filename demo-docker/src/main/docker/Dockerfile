ENV JAVA_HOME=/usr/avicare/java/jdk1.8.0_201
ENV PATH=$JAVA_HOME/bin:$PATH
ENV CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
VOLUME /tmp
ADD demo-docker.jar /opt/demo-docker/
EXPOSE 8087
WORKDIR /opt/demo-docker/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xms512m", "-Xmx1g", "-jar", "demo-docker.jar"]
