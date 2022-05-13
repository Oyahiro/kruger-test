# Kruger Test

Inventario de vacunación de empleados.

## Construcción

Sistema construido con Java 11 y Spring Boot 2.6.7.  
El sistema cuenta con la implementación del estándar JWT.  
La documentación de la API se realizó con Swagger 2.9.2.  
Se usó postgresql como gestor de base de datos en su versión 13.1.  
Se encuentra adjunto el script inicial de la base de datos, el modelo de datos y una colección en postman para probar la API.

## Despliegue
Es necesario contar con docker instalado.  
Ejecutar el comando *docker-compose up* dentro de la carpeta docker en resources.

## Indicaciones
Al registrar un nuevo empleado el endpoint retorna el nombre de usuario y la contraseña será su número de cédula.  
El nombre de usuario del administrador es *root* y la contraseña es *toor*.  
Dentro de la colección de postman se encuentran ejemplos de la ejecución para poder probar el funcionamiento del sistema.  
El puerto expuesto en docker es 8080.
