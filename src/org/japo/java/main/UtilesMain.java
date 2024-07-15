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
import java.util.Properties;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
final class UtilesMain {

    // Propiedades
    private static final String PRP_RUN_TOKEN_VALUE = "run.token.value";
    private static final String PRP_RUN_TOKEN_DISABLED = "run.token.disabled";

    // Clave de Acceso - Por defecto
    private static final String DEF_RUN_TOKEN_VALUE = "JAPO-Omicron-0";

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
            testOK = args[0].equals(DEF_RUN_TOKEN_VALUE);
        } catch (Exception e) {
            testOK = false;
        }

        // Retorno
//        testOK = true;
        return testOK;
    }

    // Argumentos + Propiedades > Validar Acceso
    static boolean validarAcceso(String[] args, Properties prp) {
        // Referencia
        boolean testOK;

        // Validación
        try {
            // Paremetros de Acceso - Token Ejecución Desactivado
            boolean runTokenDisabled = true
                    && prp.getProperty(PRP_RUN_TOKEN_DISABLED) != null
                    && prp.getProperty(PRP_RUN_TOKEN_DISABLED).equals("true");

            // Paremetros de Acceso - Valor del Token Ejecución
            String runTokenValue = prp.getProperty(PRP_RUN_TOKEN_VALUE, DEF_RUN_TOKEN_VALUE);
            
            // Utilización del Token de Ejecución
            testOK = runTokenDisabled ? true : args[0].equals(runTokenValue);
        } catch (Exception e) {
            testOK = false;
        }

        // Retorno
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
