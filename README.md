# Proyecto Parcial Mutantes

Este proyecto es una API desarrollada con Spring Boot, que permite detectar mutantes a partir de secuencias de ADN. Está conectada a una base de datos H2 para el almacenamiento de personas y estadísticas, y está desplegada en [Render](https://parcial-mutantes.onrender.com/).

## Tecnologías

- **Java 17**
- **Spring Boot 3.3.4**
- **Spring Data JPA**
- **H2 Database**
- **MapStruct 1.5.2**
- **Lombok**
- **JUnit 5**

## Repositorio en GitHub

El código fuente de este proyecto está disponible en GitHub:

- [Repositorio](https://github.com/BuchaillotBenjamin/parcial_mutantes.git)

## Despliegue en Render

La API está desplegada en Render y puedes acceder a la siguiente ruta:

- [Link Render](https://parcial-mutantes.onrender.com/)

## Funcionalidades de la API
- **POST** `/personas/mutant`: Verifica si un ADN pertenece a un mutante.
  - URL: [POST Mutant](https://parcial-mutantes.onrender.com/personas/mutant)
  
- **GET** `/stats`: Retorna las estadísticas de mutantes y humanos.
  - URL: [GET Stats](https://parcial-mutantes.onrender.com/stats)

- **GET** `/personas`: Devuelve todas las personas registradas.
  - URL: [GET Personas](https://parcial-mutantes.onrender.com/personas)

- **GET** `/personas/{id}`: Devuelve una persona por ID.
  - URL: [GET Persona por ID](https://parcial-mutantes.onrender.com/personas/1)

## Despliegue en Máquina Local

Si deseas descargar y ejecutar este proyecto en tu máquina local, sigue los siguientes pasos:

### Herramientas necesarias

Antes de ejecutar este proyecto, asegúrate de tener las siguientes herramientas instaladas en tu máquina:

1. **JDK 17**
2. **Maven** (opcional) 
3. **Gradle** (opcional) 
4. **Git** 

### 1. Clona el repositorio

   Abre una terminal o consola y clona el repositorio:

   ```bash
   git clone https://github.com/BuchaillotBenjamin/parcial_mutantes.git
