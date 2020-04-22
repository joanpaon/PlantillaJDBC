/*
 * Copyright 2016 José A. Pacheco Ondoño - joanpaon@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.japo.java.entities.DataAccessManager;
import org.japo.java.libraries.UtilesBD;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class App {

    // Propiedades Aplicación
    private final Properties prp;

    // Constructor Parametrizado
    public App(Properties prp) {
        this.prp = prp;
    }

    // Lanzar Lógica Aplicación
    public void launchApp() {
        // Mensaje Informativo
        System.out.println("Iniciando acceso a la Base de Datos ...");
        System.out.println("---");

        // Conexión BBDD + Ejecutor SQL
        try (
                Connection conn = UtilesBD.conectar(prp);
                Statement stmt = conn.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)) {
            // Mensaje Informativo
            System.out.println("Acceso a la Base de Datos INICIADO");
            System.out.println("---");

            // Gestor Acceso Datos
            System.out.println("Operaciones sobre la Base de Datos");
            System.out.println("---");
            DataAccessManager dam = new DataAccessManager(conn, stmt);

            // Lógica Aplicación
            System.out.println("Ejecución de la Lógica de la Aplicación");
            System.out.println("---");

            // Mensaje Informativo
            System.out.println("Acceso a la Base de Datos FINALIZADO");
        } catch (SQLException e) {
            System.out.println("ERROR: Acceso a la Base de Datos CANCELADO");
            System.out.printf("Código de error .: %d%n", e.getErrorCode());
            System.out.printf("Estado SQL ......: %s%n", e.getSQLState());
            System.out.printf("Descripción .....: %s%n", e.getMessage());
        } catch (Exception e) {
            System.out.printf("ERROR: Aplicación finalizada - %s%n", e.getMessage());
        }
    }
}
