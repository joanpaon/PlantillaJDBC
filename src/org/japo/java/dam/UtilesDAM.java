/*
 * Copyright 2024 José A. Pacheco - japolabs@gmail.com.
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
package org.japo.java.dam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author José A. Pacheco - japolabs@gmail.com
 */
public final class UtilesDAM {

    // Constructor Inaccesible
    private UtilesDAM() {
    }

    public static final Connection conectar(Properties prp) throws SQLException {
        // Parámetros de Conexión
        String prot = prp.getProperty(NucleoDAM.PRP_PROT);
        String host = prp.getProperty(NucleoDAM.PRP_HOST);
        String port = prp.getProperty(NucleoDAM.PRP_PORT);
        String name = prp.getProperty(NucleoDAM.PRP_NAME);
        String user = prp.getProperty(NucleoDAM.PRP_USER);
        String pass = prp.getProperty(NucleoDAM.PRP_PASS);

        // Cadena de Conexión
        String strConn = String.format(
                NucleoDAM.CONN_STR_FMT,
                prot, host, port, name,
                user, pass);

        // Realizar Conexión
        Connection conn = DriverManager.getConnection(strConn);

        // Mensaje Informativo
//        System.out.println("---");
        System.out.println("Conexión establecida con la Base de Datos");
        System.out.println("---");

        // Retorno
        return conn;
    }

    public static final Statement vincular(Connection conn, Properties prp) throws SQLException {
        // Tipo de Acceso
        int tipoAcceso = switch (prp.getProperty(NucleoDAM.PRP_TYPE)) {
            case NucleoDAM.TYPE_FO ->
                ResultSet.TYPE_FORWARD_ONLY;
            case NucleoDAM.TYPE_SI ->
                ResultSet.TYPE_SCROLL_INSENSITIVE;
            case NucleoDAM.TYPE_SS ->
                ResultSet.TYPE_SCROLL_SENSITIVE;
            default ->
                throw new SQLException("ERROR: Statement - Tipo de Acceso INDEFINIDO");
        };

        // Concurrencia
        int concurrencia = switch (prp.getProperty(NucleoDAM.PRP_CONC)) {
            case NucleoDAM.CONC_RO ->
                ResultSet.CONCUR_READ_ONLY;
            case NucleoDAM.CONC_UP ->
                ResultSet.CONCUR_UPDATABLE;
            default ->
                throw new SQLException("ERROR: Statement - Concurrencia INDEFINIDA");
        };

        // Sentencia - Statement
        Statement stmt = conn.createStatement(
                tipoAcceso,
                concurrencia);

        // Retorno
        return stmt;
    }

    public static void cerrar(Connection conn, Statement stmt) throws SQLException {
        cerrar(stmt);
        cerrar(conn);
    }

    public static void cerrar(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }

        // Mensaje Informativo
//        System.out.println("---");
        System.out.println("Conexión finalizada con la Base de Datos");
        System.out.println("---");
    }

    public static void cerrar(Statement stmt) throws SQLException {
        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
        }
    }
}
