/* 
 * Copyright 2019 José A. Pacheco Ondoño - joanpaon@gmail.com.
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
import org.japo.java.app.App;
import org.japo.java.dam.DAM;
import org.japo.java.libraries.UtilesPRP;

/**
 *
 * @author José A. Pacheco Ondoño
 */
public final class Main {

    // Constructor Inaccesible
    private Main() {
    }

    // Punto de Entrada al Programa
    public static void main(String[] args) {
        try {
            // Objeto Properties
            Properties prp = new Properties();

            // Fichero + Recursos > Propiedades
            UtilesPRP.importarPropiedades(prp);

            if (UtilesMain.validarAcceso(args, prp)) {
                // Propiedades > Conexión Base de Datos
                DAM dam = new DAM(prp);

                // Objeto Lógica de Negocio
                App app = new App(prp, dam);

                // Lanzar aplicacion
                app.launchApp();

                // Cerrar Base de Datos
                dam.cerrar();
            } else {
                UtilesMain.depurar();
            }
        } catch (SQLException e) {
            UtilesMain.depurar(e);
        } catch (Exception e) {
            UtilesMain.depurar(e);
        }
    }
}
