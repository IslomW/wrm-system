# Берём официальный образ MinIO
FROM quay.io/minio/minio:latest

# Рабочая директория (необязательно, но наглядно)
WORKDIR /data

# Открываем порты:
# 9000 — S3 API
# 9001 — Web Console
EXPOSE 9000
EXPOSE 9001

# Переменные окружения (можно переопределять при docker run)
ENV MINIO_ROOT_USER=minioadmin
ENV MINIO_ROOT_PASSWORD=minioadmin123

# Точка входа
CMD ["server", "/data", "--console-address", ":9001"]
