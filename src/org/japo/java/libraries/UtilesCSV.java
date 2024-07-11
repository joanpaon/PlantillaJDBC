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
package org.japo.java.libraries;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class UtilesCSV {

    // Separadores CSV
    public static final String SEP_CSV_LEC = "\\s*,\\s*";
    public static final String SEP_CSV_ESC = ",";

    // Fichero con Datos - CSV
    public static final String FICHERO_CSV = "datos.csv";

    // Mensajes
    public static final String MSG_ERR_CSV = "ERROR: No se puede acceder al archivo";
    public static final String MSG_ERR_LIN = "ERROR: Linea no gestionable";

    // Constructor Predeterminado ( Oculto )
    private UtilesCSV() {
    }
}
