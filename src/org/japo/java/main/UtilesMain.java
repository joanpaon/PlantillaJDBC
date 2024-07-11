/*
 * Copyright 2024 JAPO Labs - japolabs@gmail.com.
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
package org.japo.java.main;

import java.sql.SQLException;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
final class UtilesMain {

    // Clave de Acceso
    private static final String ACCESS_UID = "JAPO-Omicron-0";

    // Mensaje de Acceso Denegado
    static final String MSG_ACCESS_DENIED = """
            ERROR: Procedimiento de Acceso NO Válido
            ---
            Contacte al Servicio Técnico""";

    // Mensaje de Error en el Aceso a la Base de Datos
    static final String MSG_BD_ACCESS_ERROR = "ERROR: Base de Datos INACCESIBLE";

    // Validar Acceso
    static boolean validarAcceso(String[] args) {
        // Referencia
        boolean testOK;

        // Validación
        try {
            testOK = args[0].equals(ACCESS_UID);
        } catch (Exception e) {
            testOK = false;
        }

        // Retorno
//        testOK = true;
        return testOK;
    }

    static final void depurar() {
        System.out.println(MSG_ACCESS_DENIED);
    }

    static final void depurar(SQLException e) {
        if (e != null) {
            System.out.println(MSG_BD_ACCESS_ERROR);
            System.out.printf("Código de Error .: %d%n", e.getErrorCode());
            System.out.printf("Estado SQL. .....: %s%n", e.getSQLState());
            System.out.printf("Descripción .....: %s%n", e.getLocalizedMessage());
        }
    }

    static final void depurar(Exception e) {
        if (e != null) {
            System.out.printf("Descripción .....: %s%n", e.getLocalizedMessage());
        }
    }
}
