FROM harbor.unismartcity.com:15050/library/openjdk8:latest
# 设置启动内存、配置等
ENV JVM_MEM "-Xms128m -Xmx300m"
ENV JVM_OPT "-Dspring.profiles.active=test"
ADD target/*.jar  /data/app.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ENTRYPOINT /usr/local/openjdk-8/bin/java -jar -Dserver.port=8080 -Duser.timezone=GMT+08 $JVM_OPT $JVM_MEM /data/app.jar
