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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author José A. Pacheco - japolabs@gmail.com
 */
public final class DAM {

    // Propiedades
    private final Properties prp;

    // Conexión BD
    private final Connection conn;
    private final Statement stmt;

    // Entidades DAM
    //
    public DAM(Properties prp) throws SQLException {
        // Propiedades Aplicación
        this.prp = prp;

        // Acceso BD
        this.conn = UtilesDAM.conectar(prp);
        this.stmt = UtilesDAM.vincular(conn, prp);

        // Entidades DAM
        //
    }

    // Cerrar Base de Datos
    public final void cerrar() throws SQLException {
        UtilesDAM.cerrar(conn, stmt);
    }

    // Accesores Entidades DAM
}
