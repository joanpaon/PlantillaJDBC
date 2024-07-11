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

/**
 *
 * @author José A. Pacheco - japolabs@gmail.com
 */
final class NucleoDAM {

    // Propiedades de Conexión
    public static final String PRP_PROT = "db.conn.prot";
    public static final String PRP_HOST = "db.conn.host";
    public static final String PRP_PORT = "db.conn.port";
    public static final String PRP_NAME = "db.conn.name";
    public static final String PRP_USER = "db.conn.user";
    public static final String PRP_PASS = "db.conn.pass";

    // Propiedades de Sentencia
    public static final String PRP_TYPE = "db.stmt.access_type";
    public static final String PRP_CONC = "db.stmt.concurrency";

    // Valores Tipo de Acceso
    public static final String TYPE_FO = "TYPE_FORWARD_ONLY";
    public static final String TYPE_SI = "TYPE_SCROLL_INSENSITIVE";
    public static final String TYPE_SS = "TYPE_SCROLL_SENSITIVE";

    // Valores Concurrencia
    public static final String CONC_RO = "CONCUR_READ_ONLY";
    public static final String CONC_UP = "CONCUR_UPDATABLE";

    // Formato Cadena Conexión
    public static final String CONN_STR_FMT
            = "%s://%s:%s/%s?user=%s&password=%s";

    private NucleoDAM() {
    }
}
