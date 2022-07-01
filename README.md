# mutant-lab2
Project in charge of detecting if a human is mutant based on its DNA sequence

## Tabla de Contenido
1. [Información General](#general-info)
2. [Tecnologías](#technologies)
3. [Instalación](#installation)

### Información General
***
Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Men.

Te ha contratado a ti para que desarrolles un proyecto que detecte si un humano es mutante basándose en su secuencia de ADN.
Para eso te ha pedido crear un programa con un método o función en donde recibirás como parámetro un array de Strings que representan cada fila de una tabla de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN.

## Frameworks Tecnologías
***
Lista de tecnologías utilizadas para la realización del proyecto: 
* Java 17
* JPA
* Spring Boot 2.X
* SpringToolSuite4
* Spring Data JPA
* Spring MVC
* MapStruct
* Log4j
* JUnit5
* Maven


## Instalación
***
A continuación se describe el paso a seguir para realizar las pruebas correspondientes.

En la Carpeta Docker, se encuentran los comandos necesarios para contenerizar cada microservicio. En caso de que no se desee
realizar este paso, podemos realizar los siguiente:

```
1. clonar clonar el repositorio.
2. importar el proyecto en su framework de preferencia.
3. asegurarnos de tener todos los microservicios (eureka-server, api-gateway, mutant-lab)
4. Levantar cada microservicio en el siguiente orden:
  - eureka-server
  - api-gateway
  - mutant-lab
6. Por medio de la herramienta postman podemos utilizar las sigiuentes urls para obtener los resultados esperados.
  - POST localhost/mutant-lab/mutant
  - GET localhost/mutant-lab/stats

