package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {

    public static void main(final String[] args) throws Exception {

        String[][] automataBAH = cargarMatriz(32, 4, "resources/binahex.csv");

        String[][] automataHAA = cargarMatriz(72, 18, "resources/hexaascii.csv");

        String[] cadenaBinaria;

        String cadenaHexAux = "";
        String[] cadenaHex;

        /* ============================================================ */

        Scanner read = new Scanner(new File("resources/mensaje.txt"));
        cadenaBinaria = read.nextLine().split("");
        read.close();

        /* ============================================================ */

        // estado inicial = 0
        int estadoIdx = 1;
        int columna;

        for (int i = 0; i < cadenaBinaria.length; i++) {

            columna = buscarEntrada(cadenaBinaria[i], automataBAH, 4);
            estadoIdx = buscarEstado(automataBAH[estadoIdx][columna], automataBAH, 32);

            if (!automataBAH[estadoIdx][3].equals("-")) {
                cadenaHexAux += automataBAH[estadoIdx][3];
                estadoIdx = 1; // reseteo
            }

        }

        System.out.println(cadenaHexAux);
        System.out.println();

        /* ============================================================ */

        estadoIdx = 1;
        cadenaHex = cadenaHexAux.split("");

        for (int i = 0; i < cadenaHex.length; i++) {

            columna = buscarEntrada(cadenaHex[i], automataHAA, 18);
            estadoIdx = buscarEstado(automataHAA[estadoIdx][columna], automataHAA, 72);

            if (!automataHAA[estadoIdx][17].equals("-")) {
                System.out.print(automataHAA[estadoIdx][17]);
                estadoIdx = buscarEstado("0", automataHAA, 72);
            }

        }

        System.out.println();

    }

    public static String[][] cargarMatriz(final int filas, final int columnas, final String filePath)
            throws FileNotFoundException {

        final String[][] matriz = new String[filas][columnas];

        final Scanner read = new Scanner(new File(filePath));

        String[] line;
        int i = 0;

        while (read.hasNextLine()) {

            line = read.nextLine().split(",");

            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = line[j];
            }

            i++;

        }

        read.close();

        return matriz;

    }

    /* ============================================================ */

    public static int buscarEstado(String estado, String[][] automata, int filas) {
        for (int i = 1; i < filas; i++) {
            if (automata[i][0].equals(estado)) {
                return i;
            }
        }
        return -1;
    }

    public static int buscarEntrada(String entrada, String[][] automata, int columnas) {
        for (int i = 1; i < columnas; i++) {
            if (automata[0][i].equals(entrada)) {
                return i;
            }
        }
        return -1;
    }

}