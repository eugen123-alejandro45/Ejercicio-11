import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ejercicio11 {
    public static void main(String[] args) {
        char[] filas = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        int numFilas = filas.length;
        int numCols = 8;

        // La computadora coloca 10 barcos al azar de una sola celda
        Set<String> barcos = new HashSet<>();
        Random random = new Random();
        while (barcos.size() < 10) {
            char f = filas[random.nextInt(numFilas)];
            int c = random.nextInt(numCols) + 1;
            barcos.add(f + "" + c);
        }

        // Inicializar el tablero visible para el usuario
        char[][] tablero = new char[numFilas][numCols];
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numCols; j++) {
                tablero[i][j] = '.';
            }
        }

        Scanner scanner = new Scanner(System.in);
        int disparos = 0;
        int hundidos = 0;

        System.out.println("=== BATALLA NAVAL (8x8) ===");
        System.out.println("10 barcos ocultos. ¡Acierta sus coordenadas!");

        while (hundidos < 10) {
            // Mostrar el tablero completo con columnas y filas
            System.out.print("\n   ");
            for (int j = 1; j <= numCols; j++) {
                System.out.print(j + " ");
            }
            System.out.println();

            for (int i = 0; i < numFilas; i++) {
                System.out.print(filas[i] + "  ");
                for (int j = 0; j < numCols; j++) {
                    System.out.print(tablero[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("\n📊 Disparos realizados: " + disparos + " | Barcos hundidos: " + hundidos + "/10");
            System.out.print("Introduce coordenada (ej. A5): ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.length() < 2) {
                System.out.println("⚠️ Coordenada inválida. Usa una letra de la A a la H y un número del 1 al 8.");
                continue;
            }

            char fChar = entrada.charAt(0);
            String cStr = entrada.substring(1);

            int fIndex = -1;
            for (int i = 0; i < filas.length; i++) {
                if (filas[i] == fChar) {
                    fIndex = i;
                    break;
                }
            }

            int cIndex = -1;
            try {
                cIndex = Integer.parseInt(cStr) - 1;
            } catch (NumberFormatException e) {
                // Número inválido
            }

            if (fIndex == -1 || cIndex < 0 || cIndex >= numCols) {
                System.out.println("⚠️ Coordenada fuera de rango o formato incorrecto.");
                continue;
            }

            // Comprobar si ya se había disparado ahí
            if (tablero[fIndex][cIndex] == 'X' || tablero[fIndex][cIndex] == 'O') {
                System.out.println("⚠️ Ya habías disparado en esa casilla. Prueba otra.");
                continue;
            }

            disparos++;
            String coordenadaActual = fChar + "" + (cIndex + 1);

            // Comprobar impacto
            if (barcos.contains(coordenadaActual)) {
                System.out.println("💥 ¡Tocado y hundido!");
                tablero[fIndex][cIndex] = 'X';
                hundidos++;
            } else {
                System.out.println("💧 ¡Agua!");
                tablero[fIndex][cIndex] = 'O';
            }
        }

        System.out.println("\n🎉 ¡VICTORIA! Has completado el juego en " + disparos + " disparos totales.");
        scanner.close();
    }
}