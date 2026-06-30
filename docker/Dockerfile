# OpenMall Multi-module Application Docker Image
FROM 3bpds0vd1gewcx06p4.xuanyuan.run/library/openjdk:26-ea-21-oraclelinux8

# Set working directory
WORKDIR /app

# Copy built jar file
COPY openmall-portal/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set JVM parameters
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Start application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]