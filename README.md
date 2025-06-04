
# TFC-Backend

Este proyecto es la API del backend desarrollada en Spring Boot. 
En este documento se describen los pasos necesarios para generar la build de producción, desplegar la aplicación utilizando una imagen de Docker y, adicionalmente, cómo probar la build en local.

## Despliegue en desarrollo
Para trabajar con este proyecto en local sigue los siguientes pasos:
### Instalar Intelij
El IDE que recomiendo para probarlo en local es Intelij que se tendría que descargar:
[Descargar Intelij](https://www.jetbrains.com/es-es/idea/download/?section=windows)

### Instalar dependencias
[Node](https://nodejs.org/en/download):

````bash
npm install
````

[Maven](https://maven.apache.org/download.cgi):
````bash
mvn install
````

Ya solo faltaría iniciar el programa con el IDE

## Despliegue en producción

Al crear el proyecto se creará automáticamente un **Dockerfile** con el que podremos crear una imagen para el contenedor de Docker.

[Dockerfile Spring Boot](./FCT_BBDD/Dockerfile)

Aunque antes de empezar a hacer necesitaremos el .jar necesario para que inicie el proyecto, nos lo llevaremos a la maquina por este comando:

    scp <ruta del .jar> UsuarioReceptor@IP:<ruta destino>

Tras enviar el archivo ya podremos ejecutar el **docker-compose.yml**

[Docker-compose Spring Boot](./FCT_BBDD/docker-compose.yml)
