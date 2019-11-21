package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// https://www.traductorbinario.com/

public class App {

    public static void main(final String[] args) throws Exception {

        String[][] automataBAH = cargarMatriz(32, 4, "resources/binahex.csv");

        String[][] automataHAA = cargarMatriz(72, 18, "resources/hexaascii.csv");

        String[] cadenaBinaria;

        String cadenaHexAux = "";
        String cadenaAsciiAux = "";
        String[] cadenaHex;

        /* ============================================================ */

        Scanner read = new Scanner(new File("resources/mensaje.txt"));
        cadenaBinaria = read.nextLine().split("");
        read.close();

        /* ============================================================ */

        int columna;
        int fila = 1;
        String estado;
        String strAux = "";

        System.out.println();

        System.out.println("CONVERSOR BINARIO A HEXADECIMAL");
        System.out.println("===============================");

        Thread.sleep(1500);

        for (int i = 0; i < cadenaBinaria.length; i++) {

            columna = buscarEntrada(cadenaBinaria[i], automataBAH, 4);
            estado = automataBAH[fila][columna];
            fila = buscarEstado(estado, automataBAH, 32);

            strAux += cadenaBinaria[i];

            if (!automataBAH[fila][3].equals("-")) {
                cadenaHexAux += automataBAH[fila][3];
                System.out.println("Binario: '" + strAux + "' --> Hexadecimal: '" + automataBAH[fila][3] + "'");
                strAux = "";
            }

            Thread.sleep(25);

        }

        System.out.println();

        /* ============================================================ */

        System.out.println("CONVERSOR HEXADECIMAL A ASCII");
        System.out.println("=============================");

        Thread.sleep(1500);

        fila = 1;
        cadenaHex = cadenaHexAux.split("");

        for (int i = 0; i < cadenaHex.length; i++) {

            columna = buscarEntrada(cadenaHex[i], automataHAA, 18);
            estado = automataHAA[fila][columna];
            fila = buscarEstado(estado, automataHAA, 72);

            strAux += cadenaHex[i];

            if (!automataHAA[fila][17].equals("-")) {
                cadenaAsciiAux += automataHAA[fila][17];
                System.out.println("Hexadecimal: '" + strAux + "' --> ASCII: '" + automataHAA[fila][17] + "'");
                strAux = "";
            }

            Thread.sleep(100);

        }

        Thread.sleep(1000);


        read = new Scanner(new File("resources/mensaje.txt"));

        System.out.println("\n\nCONVERSION BINARIO A ASCII FINALIZADA.\n\nRESULTADOS:\n\nBINARIO: " + read.nextLine()
                + "\n\nHEXADECIMAL: " + cadenaHexAux + "\n\nASCII: " + cadenaAsciiAux);
        read.close();

        System.out.println("\nTERMINADO.");

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