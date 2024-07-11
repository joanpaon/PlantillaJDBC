/*
 * Copyright 2023 José A. Pacheco - japolabs@gmail.com.
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

import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author José A. Pacheco - japolabs@gmail.com
 */
public final class UtilesEntrada {

    // Mensajes Predeterminados
    public static final String MSG_USR = "Dato .....: ";
    public static final String MSG_ERR = "ERROR: Entrada incorrecta";
    public static final String MSG_BRK = "Pulse [Intro] para continuar ...";

    // Objeto Scanner
    public static final Scanner SCN
            = new Scanner(System.in, "Windows-1252")
                    .useLocale(Locale.ENGLISH).useDelimiter("\\s+");

    // Constructor Predeterminado ( Oculto )
    private UtilesEntrada() {
    }

    // Consola > Real
    public static final double obtenerReal(String msgUsr, String msgErr) {
        // Valor a obtener
        double n = 0;

        // Algoritmo de entrada
        boolean introOK = false;
        do {
            System.out.print(msgUsr);
            try {
                n = SCN.nextDouble();
                introOK = true;
            } catch (Exception e) {
                System.out.println(msgErr);
                System.out.println("---");
            } finally {
                SCN.nextLine();
            }
        } while (!introOK);

        // Retorno
        return n;
    }

    // Consola + Rango > Real
    public static final double obtenerReal(String msgUsr, String msgErr,
            double min, double max) {
        // Valor de retorno
        double n = 0;

        // Algoritmo de Entrada
        boolean introOK;
        do {
            // Entrada de Valor
            n = obtenerReal(msgUsr, msgErr);

            // Validacion de valor
            introOK = n >= min && n <= max;

            // Informar del Error
            if (!introOK) {
                System.out.println(msgErr);
                System.out.println("---");
            }

        } while (!introOK);

        // Retorno
        return n;
    }

    // Consola + Expresión Regular > Real
    public static final double obtenerReal(String msgUsr, String msgErr, String er) {
        // Referencia
        double data = 0.0;

        // Bucle Validación
        boolean dataOK;
        do {
            // Consola > Dato
            data = obtenerReal(msgUsr, msgErr);

            // Validar Dato
            dataOK = UtilesValidacion.validar(data + "", er);

            // Procesar Validación
            if (!dataOK) {
                System.out.println("---");
                System.out.println(msgErr);
                System.out.println("---");
            }
        } while (!dataOK);

        // Retorno
        return data;
    }

    // Consola > Entero
    public static final int obtenerEntero(String msgUsr, String msgErr) {
        return (int) obtenerReal(msgUsr, msgErr);
    }

    // Consola + Rango > Entero
    public static final int obtenerEntero(String msgUsr, String msgErr,
            int min, int max) {
        return (int) obtenerReal(msgUsr, msgErr, min, max);
    }

    // Consola + Expresión Regular > Entero
    public static final int obtenerEntero(String msgUsr, String msgErr, String er) {
        // Referencia
        int data = 0;

        // Bucle Validación
        boolean dataOK;
        do {
            // Consola > Dato
            data = obtenerEntero(msgUsr, msgErr);

            // Validar Dato
            dataOK = UtilesValidacion.validar(data + "", er);

            // Procesar Validación
            if (!dataOK) {
                System.out.println("---");
                System.out.println(msgErr);
                System.out.println("---");
            }
        } while (!dataOK);

        // Retorno
        return data;
    }

    // Consola > Carácter
    public static final char obtenerCaracter(String msgUsr, String msgErr) {
        // Valor a obtener
        char c = 0;

        // Algoritmo de entrada
        boolean introOK = false;
        do {
            System.out.print(msgUsr);
            try {
                c = SCN.nextLine().charAt(0);
                introOK = true;
            } catch (Exception e) {
                System.out.println("---");
                System.out.println(msgErr);
                System.out.println("---");
            }
        } while (!introOK);

        // Retorno
        return c;
    }

    // Consola + Opciones > Carácter
    public static final char obtenerCaracter(String msgUsr, String msgErr, String opc) {
        // Valor a obtener
        char c = 0;

        // Algoritmo de entrada
        boolean introOK = false;
        do {
            c = obtenerCaracter(msgUsr, msgErr);
            if (opc.contains(c + "")) {
                introOK = true;
            } else {
                System.out.println("---");
                System.out.println(msgErr);
            }
            System.out.println("---");
        } while (!introOK);

        // Retorno
        return c;
    }

    // Consola + Rango > Carácter
    public static final char obtenerCaracter(String msgUsr, String msgErr,
            char min, char max) {
        // Valor de retorno
        char c = 0;

        // Algoritmo de Entrada
        boolean introOK;
        do {
            // Entrada de Valor
            c = obtenerCaracter(msgUsr, msgErr);

            // Validacion de valor
            introOK = c >= min && c <= max;

            // Informar del Error
            if (!introOK) {
                System.out.println(msgErr);
                System.out.println("---");
            }

        } while (!introOK);

        // Retorno
        return c;
    }

    // Consola > Texto
    public static final String obtenerTexto(String msgUsr) {
        System.out.print(msgUsr);
        return SCN.nextLine();
    }

    // Consola + Expresión Regular > Texto
    public static final String obtenerTexto(String msgUsr, String msgErr, String er) {
        String data = "";
        boolean dataOK;

        do {
            data = obtenerTexto(msgUsr);

            dataOK = UtilesValidacion.validar(data, er);

            if (!dataOK) {
                System.out.println("---");
                System.out.println(msgErr);
                System.out.println("---");
            }
        } while (!dataOK);

        return data;
    }

    // Consola > Lógico
    public static final boolean obtenerLogico(String msgUsr, String msgErr) {
        // Valor de retorno
        boolean b = false;

        // Entrada de Valor
        boolean introOK = false;
        do {
            try {
                System.out.print(msgUsr);
                b = SCN.nextBoolean();
                introOK = true;
            } catch (Exception e) {
                System.out.println(msgErr);
            } finally {
                SCN.nextLine();
            }
        } while (!introOK);

        // Retorno
        return b;
    }

    // Mensaje Personalizado + Pausa ( Intro )
    public static final void hacerPausa(String msg) {
        System.out.println(msg);
        hacerPausa();
    }

    // Pausa ( Intro )
    public static final void hacerPausa() {
        System.out.println("---");
        obtenerTexto(MSG_BRK);
        System.out.println("---");
    }
}
