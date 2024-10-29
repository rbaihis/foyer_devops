FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY ${JAR_FILE} /app/foyer-devops.jar

# Expose the port that the application will run on
EXPOSE 8079

# Set default environment variables (can be overridden at runtime)
ENV SERVER_PORT=8079 \
    CONTEXT_PATH='/api/foyer' \
    APP_API_SETTINGS_CROSS_ORIGIN_PATTERNS='*' \
    DB_HOST=mysql \
    DB_PORT=3306 \
    DB_NAME=foyer-devops-db \
    DB_USERNAME=root \
    DB_PASSWORD='' \
    DDL_AUTO_STRATEGY=update \
    SHOW_SQL=false \
    LOG_PATH='/logs/foyer-devops.log' \
    LOGGING_LEVEL_COM_ZAXXER_HIKARI=warn \
    LOGGING_LEVEL_ORG_SPRINGFRAMEWORK=warn \
    LOGGING_LEVEL_ROOT=INFO \
    LOGGING_PATTERN_CONSOLE="%d{yyyy-MM-dd HH:mm:ss} - %-5level - %logger{60} - %msg%n" \
    LOGGING_PATTERN_FILE="%d{yyyy-MM-dd HH:mm:ss} - %-5level - %logger{60} - %msg%n" \
    MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=prometheus \
    MANAGEMENT_ENDPOINTS_WEB_BASE_PATH='/actuator' \
    MANAGEMENT_ENDPOINT_PROMETHEUS_ENABLED=true \
    MANAGEMENT_METRICS_TAGS_APPLICATION=foyer-app \
    MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS=always \
    MANAGEMENT_ENDPOINT_HEALTH_ENABLED=true \
    MANAGEMENT_ENDPOINT_METRICS_ENABLED=true

RUN mkdir -p /logs

ENTRYPOINT ["java", "-jar", "foyer-devops.jar"]