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
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.japo.java.libraries.UtilesEntrada;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class DataAccessManager {

    // Sentencias SQL - Módulos
    public static final String DEF_MOD_SQL1 = "SELECT * FROM modulo";
    public static final String DEF_MOD_SQL2 = "DELETE FROM modulo WHERE acronimo='SI'";
    public static final String DEF_MOD_SQL3
            = "INSERT INTO modulo "
            + "(id, acronimo, nombre, codigo, horasCurso, curso) "
            + "VALUES "
            + "(5, 'SI', 'Sistemas Informáticos', 'MP0482', 96, 1)";
    public static final String DEF_MOD_SQL4 = "UPDATE modulo SET curso=2 WHERE horasCurso<100";
    public static final String DEF_MOD_SQL5 = "SELECT * FROM modulo WHERE id='%s'";
    public static final String DEF_MOD_SQL6
            = "INSERT INTO modulo "
            + "(id, acronimo, nombre, codigo, horasCurso, curso) "
            + "VALUES "
            + "(?, ?, ?, ?, ?, ?)";
    
    // Sentencias SQL - Alumnos
    public static final String DEF_ALU_SQL1 = "SELECT * FROM alumno";

    // Sentencias SQL - Profesores
    public static final String DEF_PRF_SQL1 = "SELECT * FROM profesor";

    // Tamaño Paginación
    public static final int DEF_LIN_PAG = 3;

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

    // Cabecera Registro Modulo
    private static final String CAB_REG_MOD1 = "Proceso de BORRADO de Módulos - Registro %03d";
    private static final String CAB_REG_MOD2 = "============================================";

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

    // Metadatos BD >> Pantalla
    public final void mostrarMetadatos() throws SQLException {
        // Metadatos de la base de datos
        DatabaseMetaData dmd = con.getMetaData();

        // Cabecera
        System.out.println("Información");
        System.out.println("===========");

        // Información de la base de datos
        System.out.printf("Usuario .........: %s%n", dmd.getUserName());
        System.out.printf("Base de datos ...: %s%n", dmd.getDatabaseProductName());
        System.out.printf("Versión SGBD ....: %s%n", dmd.getDatabaseProductVersion());
        System.out.printf("Driver JDBC .....: %s%n", dmd.getDriverName());
        System.out.printf("Versión JDBC ....: %d.%d%n",
                dmd.getJDBCMajorVersion(),
                dmd.getJDBCMinorVersion());
    }

    // Listado Módulos - SQL
    public final void listar(String sql) throws SQLException {
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
                    // Obtiene los datos del registro actual
                    // Los campos se refieren por su nombre o por su posición
                    // Por su nombre, debe ser exactamente el mismo de la tabla
                    // Por su posición, los campos se numeran a partir del 1
                    // de su posición en la sentencia SQL que los ha obtenido
                    int fila = rs.getRow();
                    int id = rs.getInt("id");
                    String acronimo = rs.getString("acronimo");
                    String nombre = rs.getString("nombre");
                    String codigo = rs.getString("codigo");
                    int horas = rs.getInt("horasCurso");
                    int curso = rs.getInt("curso");

                    // Imprime los datos del registro actual
                    System.out.printf("%03d %-11d %-10s %-25s %-10s %4d %4d%n",
                            fila, id, acronimo, nombre, codigo, horas, curso);
                } while (rs.next());
            } else {
                System.out.println("No hay módulos que mostrar ...");
            }
        }
    }

    // Listado Módulos - SQL + Paginado
    public final void listar(String sql, int lineasPagina) throws SQLException {
        // Comprueba Lineas de Paginación
        if (lineasPagina > 0) {
            // Mensaje de inicio de listado
            System.out.println("Listado de módulos ...");
            System.out.println("---");

            // Proceso de listado
            try (ResultSet rs = stmt.executeQuery(sql)) {
                // Comprueba si hay datos
                if (rs.next()) {
                    //<editor-fold defaultstate="collapsed" desc="Bucle Recorrido Registros">
                    // Bucle Recorrido Registros
                    boolean nuevoRegistroOK;
                    int lineaAct = 1;
                    int paginaAct = 1;
                    do {
                        // Cabecera
                        System.out.printf("Página ...: %02d%n", paginaAct);
                        System.out.println("--------------");
                        System.out.println(CAB_LST_MOD1);
                        System.out.println(CAB_LST_MOD2);

                        //<editor-fold defaultstate="collapsed" desc="Bucle Recorrido Página">
                        // Bucle Recorrido Página
                        do {
                            // Extraer Datos Fila Actual
                            int numRegistro = rs.getRow();
                            int id = rs.getInt("id");
                            String acronimo = rs.getString("acronimo");
                            String nombre = rs.getString("nombre");
                            String codigo = rs.getString("codigo");
                            int horas = rs.getInt("horasCurso");
                            int curso = rs.getInt("curso");

                            // Mostrar Datos Fila/Linea Actual
                            System.out.printf("%03d %-11d %-10s %-25s %-10s %4d %4d%n",
                                    numRegistro, id, acronimo, nombre, codigo, horas, curso);

                            // Actualiza Linea Actual
                            lineaAct++;

                            // Nuevo Registro
                            nuevoRegistroOK = rs.next();
                        } while (lineaAct <= lineasPagina && nuevoRegistroOK);
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Análisis Fin Página">
                        // Análisis Fin Página
                        if (nuevoRegistroOK) {
                            // Separación
                            System.out.println("---");

                            // Confirmación
                            char respuesta = UtilesEntrada.leerOpcion(
                                    "SsNn",
                                    "Siguiente Página (S/N) ...: ",
                                    "ERROR: Entrada incorrecta");

                            // Análisis Respuesta
                            if (respuesta == 'S' || respuesta == 's') {
                                paginaAct++;

                                // Inicializa Paginación
                                lineaAct = 1;

                                // Separación
                                System.out.println("---");
                            } else {
                                nuevoRegistroOK = false;
                            }
                        }
                        //</editor-fold>
                    } while (nuevoRegistroOK);
                    //</editor-fold>
                } else {
                    System.out.println("No hay módulos que mostrar ...");
                }
            }
        } else {
            listar(sql);
        }
    }

    // Borrado Módulos - Búsqueda
    public void borrar() throws SQLException {
        // Mensaje de inicio de Inserción
        System.out.println("Borrado de módulos ...");
        System.out.println("---");

        // Entrada Clave Búsqueda
        int id = UtilesEntrada.leerEntero("Id ..........: ", "ERROR: Entrada incorrecta");
        System.out.println("---");

        // Proceso de Actualización
        try (ResultSet rs = stmt.executeQuery(String.format(DEF_MOD_SQL5, id + ""))) {
            // Análisis Búsqueda
            if (rs.next()) {
                System.out.printf("Acrónimo ....: %s%n", rs.getString("acronimo"));
                System.out.printf("Nombre ......: %s%n", rs.getString("nombre"));
                System.out.printf("Código ......: %s%n", rs.getString("codigo"));
                System.out.printf("Horas .......: %d%n", rs.getInt("horasCurso"));
                System.out.printf("Curso .......: %d%n", rs.getInt("curso"));
                System.out.println("---");

                // Confirmación
                char respuesta = UtilesEntrada.leerOpcion(
                        "SsNn",
                        "Borrar Módulo (S/N) ...: ",
                        "ERROR: Entrada incorrecta");

                // Separador
                System.out.println("---");

                // Análisis Respuesta
                if (respuesta == 'S' || respuesta == 's') {
                    rs.deleteRow();
                    System.out.println("Borrado de módulo REALIZADO");
                } else {
                    System.out.println("Borrado de módulo CANCELADO");
                }
            } else {
                System.out.println("ERROR: No se encuentra el módulo buscado");
            }
        }
    }

    // Borrado Módulos - SQL
    public final void borrar(String sql) throws SQLException {
        // Mensaje de inicio de listado
        System.out.println("Borrado de módulos ...");

        // Separación
        System.out.println("---");

        // Borrado de datos
        int numReg = stmt.executeUpdate(sql);

        // Muestra Registros Borrados
        System.out.println(numReg + " registro/s borrado/s");
    }

    // Borrado Módulos - Interactivo
    public final void borrar(String sql, boolean userOK) throws SQLException {
        // Mensaje de inicio de Borrado
        System.out.println("Borrado de módulos ...");
        System.out.println("---");

        // Proceso de Borrado
        try (ResultSet rs = stmt.executeQuery(sql)) {
            // Contador Registros Borrados
            int regBorrados = 0;

            // Recorrido de Registros
            while (rs.next()) {
                // Cabecera
                System.out.printf(CAB_REG_MOD1 + "%n", rs.getRow());
                System.out.println(CAB_REG_MOD2);

                // Mostrar Módulo Actual
                System.out.printf("Id .........: %d%n", rs.getInt("id"));
                System.out.printf("Acrónimo ...: %s%n", rs.getString("acronimo"));
                System.out.printf("Nombre .....: %s%n", rs.getString("nombre"));
                System.out.printf("Código .....: %s%n", rs.getString("codigo"));
                System.out.printf("Acrónimo ...: %d%n", rs.getInt("horasCurso"));
                System.out.printf("Acrónimo ...: %d%n", rs.getInt("curso"));
                System.out.println("---");

                // Interactividad
                if (userOK) {
                    // Confirmación
                    char respuesta = UtilesEntrada.leerOpcion(
                            "SsNn",
                            "Borrar Módulo (S/N) ...: ",
                            "ERROR: Entrada incorrecta");

                    // Análisis Respuesta
                    if (respuesta == 'S' || respuesta == 's') {
                        // Borrado Modulo
                        rs.deleteRow();

                        // Actualiza Contador
                        regBorrados++;

                        // Mensaje Borrado
                        System.out.println("---");
                        System.out.println("Módulo actual borrado ...");
                    }

                    // Separador
                    System.out.println("---");
                } else {
                    // Borrado Modulo
                    rs.deleteRow();

                    // Actualiza Contador
                    regBorrados++;

                    // Mensaje Borrado
                    System.out.println("---");
                    System.out.println("Módulo actual borrado ...");
                    System.out.println("---");
                }
            }

            // Muestra las filas borradas
            System.out.printf("Se han borrado %d módulos%n", regBorrados);
        }
    }

    // Inserción Módulos - SQL
    public final void insertar(String sql) throws SQLException {
        // Mensaje de inicio de listado
        System.out.println("Inserción de módulos ...");

        // Separación
        System.out.println("---");

        // Inserción de datos
        int filas = stmt.executeUpdate(sql);

        // Muestra las filas borradas
        System.out.println(filas + " fila/s insertadas/s");
    }

    // Inserción Módulos - Interactivo 
    public final void insertar() throws SQLException {
        // Mensaje de inicio de Inserción
        System.out.println("Inserción de módulos ...");
        System.out.println("---");

        // Entrada Clave Búsqueda
        int id = UtilesEntrada.leerEntero("Id .........: ", "ERROR: Entrada incorrecta");
        System.out.println("---");

        // Proceso de Inserción
        try (ResultSet rs = stmt.executeQuery(String.format(DEF_MOD_SQL5, id + ""))) {
            if (rs.next()) {
                System.out.println("ERROR: Ya existe el registro que se quiere insertar");
                System.out.println("---");

                // Muestra Módulo encontrado
                System.out.println("Registro Actual");
                System.out.println("===============");
                System.out.printf("Acrónimo ....: %s%n", rs.getString("acronimo"));
                System.out.printf("Nombre ......: %s%n", rs.getString("nombre"));
                System.out.printf("Código ......: %s%n", rs.getString("codigo"));
                System.out.printf("Horas .......: %d%n", rs.getInt("horasCurso"));
                System.out.printf("Curso .......: %d%n", rs.getInt("curso"));
                System.out.println("---");
            } else {
                // Posicionar Fila Inserción
                rs.moveToInsertRow();

                // Trasvase de Datos
                rs.updateInt(1, id);
                rs.updateString(2, UtilesEntrada.leerTexto("Acrónimo ...: "));
                rs.updateString(3, UtilesEntrada.leerTexto("Nombre .....: "));
                rs.updateString(4, UtilesEntrada.leerTexto("Código .....: "));
                rs.updateInt(5, UtilesEntrada.leerEntero("Horas ......: ", "ERROR: Entrada incorrecta"));
                rs.updateInt(6, UtilesEntrada.leerEntero("Curso ......: ", "ERROR: Entrada incorrecta"));

                // Separador
                System.out.println("---");

                // Confirmación
                char respuesta = UtilesEntrada.leerOpcion(
                        "SsNn",
                        "Insertar Módulo (S/N) ...: ",
                        "ERROR: Entrada incorrecta");

                // Análisis Respuesta
                if (respuesta == 'S' || respuesta == 's') {
                    // Inserción Modulo
                    rs.insertRow();

                    // Mensaje Informativo
                    System.out.println("---");
                    System.out.println("Inserción de datos REALIZADA");
                } else {
                    // Mensaje Informativo
                    System.out.println("---");
                    System.out.println("Inserción de datos CANCELADA");
                }

                // Continuar Acceso
                rs.moveToCurrentRow();
            }
        }
    }

    // Modificación Módulos - SQL
    public final void modificar(String sql) throws SQLException {
        // Mensaje de inicio de listado
        System.out.println("Modificación de módulos ...");

        // Separación
        System.out.println("---");

        // Inserción de datos
        int filas = stmt.executeUpdate(sql);

        // Muestra las filas borradas
        System.out.println(filas + " fila/s modificadas/s");
    }

    // Modificación Módulos - Interactivo
    public final void modificar() throws SQLException {
        // Mensaje de inicio de Inserción
        System.out.println("Actualización de módulos ...");
        System.out.println("---");

        // Entrada Clave Búsqueda
        int id = UtilesEntrada.leerEntero("Id .........: ", "ERROR: Entrada incorrecta");
        System.out.println("---");

        // Proceso de Actualización
        try (ResultSet rs = stmt.executeQuery(String.format(DEF_MOD_SQL5, id + ""))) {
            // Análisis Búsqueda
            if (rs.next()) {
                // Muestra Módulo encontrado
                System.out.println("Registro Actual - Estado Inicial");
                System.out.println("================================");
                System.out.printf("Acrónimo ....: %s%n", rs.getString("acronimo"));
                System.out.printf("Nombre ......: %s%n", rs.getString("nombre"));
                System.out.printf("Código ......: %s%n", rs.getString("codigo"));
                System.out.printf("Horas .......: %d%n", rs.getInt("horasCurso"));
                System.out.printf("Curso .......: %d%n", rs.getInt("curso"));
                System.out.println("---");

                // Trasvase de Datos
                System.out.println("Registro Actual - Estado Final");
                System.out.println("==============================");
                rs.updateString(2, UtilesEntrada.leerTexto("Acrónimo ....: "));
                rs.updateString(3, UtilesEntrada.leerTexto("Nombre ......: "));
                rs.updateString(4, UtilesEntrada.leerTexto("Código ......: "));
                rs.updateInt(5, UtilesEntrada.leerEntero("Horas .......: ", "ERROR: Entrada incorrecta"));
                rs.updateInt(6, UtilesEntrada.leerEntero("Curso .......: ", "ERROR: Entrada incorrecta"));
                System.out.println("---");

                // Confirmación
                char respuesta = UtilesEntrada.leerOpcion(
                        "SsNn",
                        "Actualizar Módulo (S/N) ...: ",
                        "ERROR: Entrada incorrecta");

                // Separador
                System.out.println("---");

                // Análisis Respuesta
                if (respuesta == 'S' || respuesta == 's') {
                    rs.updateRow();
                    System.out.println("Actualización de datos REALIZADA");
                } else {
                    System.out.println("Actualización de datos CANCELADA");
                }
            } else {
                System.out.println("ERROR: No hay datos asociados a la búsqueda");
            }
        }
    }

    // Inserción Módulos - SQL Preparado
    public void insertarPreparado() throws SQLException {
        // Mensaje de inicio de inserción
        System.out.println("Inserción de módulos ...");
        System.out.println("---");

        // Entrada Clave Búsqueda
        int id = UtilesEntrada.leerEntero("Id ..........: ", "ERROR: Entrada incorrecta");
        System.out.println("---");

        // Proceso de Inserción
        try (ResultSet rs = stmt.executeQuery(String.format(DEF_MOD_SQL5, id + ""))) {
            // Análisis Búsqueda
            if (rs.next()) {
                System.out.println("ERROR: Ya existe el módulo a insertar");
            } else {
                try (PreparedStatement pstmt = con.prepareStatement(DEF_MOD_SQL6)) {
                    // Registros Insertados
                    int numReg = 0;

                    // Valores > Parámetros
                    pstmt.setInt(1, id);
                    pstmt.setString(2, UtilesEntrada.leerTexto("Acrónimo ....: "));
                    pstmt.setString(3, UtilesEntrada.leerTexto("Nombre ......: "));
                    pstmt.setString(4, UtilesEntrada.leerTexto("Código ......: "));
                    pstmt.setInt(5, UtilesEntrada.leerEntero("Horas .......: ", "ERROR: Entrada incorrecta"));
                    pstmt.setInt(6, UtilesEntrada.leerEntero("Curso .......: ", "ERROR: Entrada incorrecta"));
                    System.out.println("---");

                    // Confirmación
                    char respuesta = UtilesEntrada.leerOpcion(
                            "SsNn",
                            "Insertar Módulo (S/N) ...: ",
                            "ERROR: Entrada incorrecta");

                    // Separador
                    System.out.println("---");

                    // Análisis Respuesta
                    if (respuesta == 'S' || respuesta == 's') {
                        numReg = pstmt.executeUpdate();
                        System.out.println("Inserción de datos REALIZADA");
                    } else {
                        System.out.println("Inserción de datos CANCELADA");
                    }

                    // Número Registros Insertados
                    System.out.println(numReg + " registro/s insertado/s.");
                }
            }
        }
    }
}
