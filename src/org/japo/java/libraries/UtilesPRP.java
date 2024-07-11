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
package org.japo.java.libraries;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author José A. Pacheco - japolabs@gmail.com
 */
public final class UtilesPRP {

    // Mensajes de Error
    public static final String MSG_PRP_SAVE_NO = "ERROR: Propiedades NO guardadas";
    public static final String MSG_PRP_LOAD_NO = "ERROR: Propiedades NO cargadas";

    // Mensajes de Aviso
    public static final String MSG_PRP_SAVE_OK = "Propiedades guardadas correctamente";
    public static final String MSG_PRP_LOAD_OK = "Propiedades leídas correctamente";

    // Ficheros de Propiedades
    public static final String FICHERO_IN = "app.properties";
    public static final String FICHERO_OUT = "app.properties";
    public static final String PAQUETE = "config";
    public static final String RECURSO = PAQUETE + "/" + FICHERO_IN;


    private UtilesPRP() {
    }

    // Propiedades > Fichero
    public static final boolean exportarPropiedades(Properties prp, String fichero) {
        // Semáforo de Estado
        boolean procesoOK = false;

        // Guardado de Propiedades
        try (FileWriter fr = new FileWriter(fichero)) {
            // Propiedades > Fichero
            prp.store(fr, null);

            // Proceso OK
            procesoOK = true;

            // Mensaje
//            System.out.println(MSG_PRP_SAVE_OK);
        } catch (Exception e) {
//            System.out.println(MSG_PRP_SAVE_NO);
        }

        // Retorno
        return procesoOK;
    }

    // Propiedades > Fichero
    public static final boolean exportarPropiedadesFichero(Properties prp) {
        return exportarPropiedades(prp, FICHERO_OUT);
    }

    // Propiedades > Fichero
    public static final boolean importarPropiedadesFichero(Properties prp, String fichero) {
        // Semáforo de Estado
        boolean procesoOK = false;

        // Lectura de Propiedades
        try (FileReader fr = new FileReader(fichero)) {
            // Propiedades > Fichero
            prp.load(fr);

            // Proceso OK
            procesoOK = true;

            // Mensaje
//            System.out.println(MSG_PRP_LOAD_OK);
        } catch (Exception e) {
//            System.out.println(MSG_PRP_LOAD_NO);
        }

        // Retorno
        return procesoOK;
    }

    // Propiedades > Fichero
    public static final boolean importarPropiedadesFichero(Properties prp) {
        return UtilesPRP.importarPropiedadesFichero(prp, FICHERO_OUT);
    }

    // Propiedades > Fichero
    public static final boolean importarPropiedadesRecurso(Properties prp, String recurso) {
        // Semáforo de Estado
        boolean procesoOK = false;

        try (InputStream is = ClassLoader.getSystemResourceAsStream(recurso)) {
            prp.load(is);
        } catch (Exception ex) {
//            System.out.println(MSG_PRP_LOAD_NO);
        }

        // Retorno
        return procesoOK;
    }

    // Propiedades > Fichero
    public static final boolean importarPropiedadesRecurso(Properties prp) {
        return importarPropiedadesRecurso(prp, RECURSO);
    }
    
    public static final boolean importarPropiedades(Properties prp) {
        boolean prpFicOK = importarPropiedadesFichero(prp);
        boolean prpRecOK = importarPropiedadesRecurso(prp);
        return prpFicOK && prpRecOK;
    }
}
