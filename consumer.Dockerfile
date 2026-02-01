# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# parent ve tüm modülleri kopyala
COPY pom.xml .
COPY core ./core
COPY data ./data
COPY coupon-command-service ./coupon-command-service
COPY coupon-consumer-service ./coupon-consumer-service
COPY coupon-scheduler-service ./coupon-scheduler-service

RUN mvn -am package -DskipTests

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# ---- Database ----
ENV DB_URL=jdbc:postgresql://postgre/coupon
ENV DB_USERNAME=adminnn
ENV DB_PASSWORD=adminnn

# ---- Kafka ----
ENV KAFKA_BOOTSTRAP_ADRESS=kafka:29092



COPY --from=build /build/coupon-consumer-service/target/*jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]