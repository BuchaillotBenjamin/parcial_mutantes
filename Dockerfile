# Fase de construcción
FROM alpine:latest AS build

# Actualizar los paquetes e instalar Java 17
RUN apk update && apk add openjdk17

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar todo el proyecto al directorio de trabajo del contenedor
COPY . .

# Dar permisos de ejecución al script gradlew y generar el JAR
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# Verificar si el archivo JAR se generó
RUN ls -la /app/build/libs

# Fase de ejecución
FROM openjdk:17-alpine

# Exponer el puerto en el que corre la aplicación
EXPOSE 9000

# Establecer el directorio de trabajo para la fase de ejecución
WORKDIR /app

# Copiar el archivo JAR generado desde la fase de construcción
COPY --from=build /app/build/libs/*.jar ./app.jar

# Definir el comando de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
