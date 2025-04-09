# TFC-Backend

## Creación de imagen
Al crear el proyecto se creará automáticamente un **Dockerfile** con el que podremos crear una imagen para el contenedor de Docker.

[Dockerfile Spring Boot](./FCT_BBDD/Dockerfile)

Aunque antes de empezar a hacer necesitaremos el .jar necesario para que inicie el proyecto, nos lo llevaremos a la maquina por este comando:

    scp <ruta del .jar> UsuarioReceptor@IP:<ruta destino>

Tras enviar el archivo ya podremos ejecutar el **docker-compose.yml**

[Docker-compose Spring Boot](./FCT_BBDD/docker-compose.yml)