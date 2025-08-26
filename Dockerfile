# 使用 Maven 构建阶段
FROM maven:3.8.4-openjdk-8 AS build

# 设置工作目录
WORKDIR /app

# 将 pom.xml 和 src 复制到工作目录
COPY back/pom.xml .
COPY back/src ./src

# 构建应用
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 jar 文件
COPY --from=build /app/target/*.jar app.jar

# 暴露端口 8080
EXPOSE 8080

# 运行应用程序，指定使用生产环境配置并限制内存使用
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dspring.config.location=file:/etc/secrets/application-prod.yml", "-Xmx300m", "-Xms128m", "-XX:MaxMetaspaceSize=128m", "-jar", "app.jar"]