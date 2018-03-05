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
    private static final String DEF_SQL_MOD1 = "SELECT * FROM modulo";
    private static final String DEF_SQL_ALU1 = "SELECT * FROM alumno";
    private static final String DEF_SQL_PRO1 = "SELECT * FROM profesor";

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
}
