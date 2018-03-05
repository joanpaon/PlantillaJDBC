/*
 * Copyright 2018 José A. Pacheco Ondoño - joanpaon@gmail.com.
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
package org.japo.java.entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class DataAccessManager {

    // Sentencias SQL
    private static final String DEF_SQL_MOD = "SELECT * FROM modulo";
    private static final String DEF_SQL_ALU = "SELECT * FROM alumno";
    private static final String DEF_SQL_PRO = "SELECT * FROM profesor";

    // Cabecera Módulos
    private static final String CAB_LST_MOD1
            = " #  Id          Acrónimo   Nombre                    Código     Horas Curso";
    private static final String CAB_LST_MOD2
            = "=== =========== ========== ========================= ========== ===== =====";

    // Cabecera Alumnos
    private static final String CAB_LST_ALU1
            = " #  ";
    private static final String CAB_LST_ALU2
            = "=== ";

    // Cabecera Profesores
    private static final String CAB_LST_PRO1
            = " #  ";
    private static final String CAB_LST_PRO2
            = "=== ";

    // Referencias
    private Connection con;
    private Statement stmt;

    // Constructor - Connection
    public DataAccessManager(Connection con) {
        this.con = con;
    }

    // Constructor - Connection + Statement
    public DataAccessManager(Connection con, Statement stmt) {
        this.con = con;
        this.stmt = stmt;
    }

    // --- Accesores ---
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }
    // --- Accesores ---

    // Módulos BD >> Módulos Listado
    public final void listarModulos() throws SQLException {
        listarModulos(stmt, DEF_SQL_MOD);
    }

    // Módulos BD >> Módulos Listado
    private void listarModulos(Statement stmt, String sql) throws SQLException {
        // Proceso de listado
        try (ResultSet rs = stmt.executeQuery(sql)) {
            // Mensaje de inicio de listado
            System.out.println("Listado de módulos ...");

            // Separación
            System.out.println("---");

            // Comprueba si hay datos
            if (rs.next()) {
                // Cabecera
                System.out.println(CAB_LST_MOD1);
                System.out.println(CAB_LST_MOD2);

                // Generación del informe - Fila a fila
                do {
                    // Línea de texto con los datos de la fila actual
                    // Los campos se refieren por su nombre o por su posición
                    // Por su nombre, debe ser exactamente el mismo en la tabla
                    // Por su posición, los campos se numeran a partir de 1
                    String fila = String.format("%03d ", rs.getRow());
                    String id = String.format("%-11d ", rs.getInt("id"));
                    String acronimo = String.format("%-10s ", rs.getString("acronimo"));
                    String nombre = String.format("%-25s ", rs.getString("nombre"));
                    String codigo = String.format("%-10s ", rs.getString("codigo"));
                    String horas = String.format("%4d ", rs.getInt("horasCurso"));
                    String curso = String.format("%4d", rs.getInt("curso"));
                    System.out.println(fila + id + acronimo + nombre + codigo + horas + curso);

                    // Línea de texto con los datos de la fila actual
                    // Los campos se refieren por su nombre o por su posición
                    // Por su nombre, debe ser exactamente el mismo en la tabla
                    // Por su posición, los campos se numeran a partir de 1
//                    System.out.println(
//                            String.format("%03d ", rs.getRow())
//                            + String.format("%-11d ", rs.getInt("id"))
//                            + String.format("%-10s ", rs.getString("acronimo"))
//                            + String.format("%-25s ", rs.getString("nombre"))
//                            + String.format("%-10s ", rs.getString("codigo"))
//                            + String.format("%4d ", rs.getInt("horasCurso"))
//                            + String.format("%4d", rs.getInt("curso")));
                } while (rs.next());
            } else {
                System.out.println("No hay módulos que mostrar ...");
            }

            // Separación
            System.out.println("---");
        }
    }
}
