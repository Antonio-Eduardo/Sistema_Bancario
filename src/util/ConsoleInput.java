package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInput {

    public static int lerInteiros(Scanner sc, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = sc.nextInt();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Numero invalido");
                sc.nextLine();
            }
        }
    }
    public static double lerDouble(Scanner sc, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valorDouble = sc.nextDouble();
                sc.nextLine();
                return valorDouble;

            } catch (InputMismatchException e) {
                System.out.println("Numero invalido");
                sc.nextLine();
            }
        }
    }
    public static String lerString(Scanner sc, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String entrada = sc.nextLine();
                if (entrada.matches(".*\\d.*")) {
                    throw new IllegalArgumentException("Erro: O nome so pode contar letras.");
                } else if (entrada.trim().isEmpty()) {
                    throw new IllegalArgumentException(("O nome não pode ser vazio."));
                } else {
                    return entrada;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Nome Invalido! " + e.getMessage());
            }
        }
    }
    public static Long lerLong(Scanner sc, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                Long valor = sc.nextLong();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Numero invalido");
                sc.nextLine();
            }
        }
    }
}





