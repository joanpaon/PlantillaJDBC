<pre>

Project .....: PlantillaJDBC
Description .: Plantilla para realizar Proyectos JDBC simples
               utilizando la BD MariaDB, incluida en el paquete 
               XAMPP, con salida en Consola y utilizando el IDE
               NetBeans
Creation ....: 27/02/2018
Update ......: 11/07/2024
Author ......: José A. Pacheco Ondoño - joanpaon@gmail.com
Language ....: Java 21 LTS
IDE .........: NetBeans 22
DB Brand ....: MariaDB 10.4.32 ( XAMPP 8.2.12 Stack )
JDBC Driver .: mariadb-java-client-3.4.0.jar
               waffle-jna-3.4.0.jar ( dependency )

This is a modular project.

By default, in the file: config>app.properties, is assumed that
there is a working user defined in the database with proper 
rights:
Username ....: usuario
Password ....: usuario
Probably, you should change it for your own.

By default, in the file: config>app.properties, is assumed that
there is a database called: test. This is only for checking 
purposes. You should change for the one you want. Including
DTO Entities according to tables defined inside.

You MUST provide the token: JAPO-Omicron-0, as
the command line run only parameter in the project 
Properties>Run>Arguments. 

You can define a custom config for that called JAPO, if you 
want. ;)

Have fun!

</pre>
