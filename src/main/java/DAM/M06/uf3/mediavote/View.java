package DAM.M06.uf3.mediavote;

import java.util.Scanner;

public class View {
    private static final Scanner scanner = new Scanner(System.in);

    // Mostrar el menú principal después de iniciar sesión o registrarse
    public static int showMenu() {
        System.out.println("\n----- MENÚ PRINCIPAL -----");
        System.out.println("1. Ver documentos");
        System.out.println("2. Realizar otra acción");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
        return scanner.nextInt();
    }

    // Mostrar el menú de inicio de sesión o registro
    public static int showLoginMenu() {
        System.out.println("\n----- MENÚ DE INICIO DE SESIÓN -----");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
        return scanner.nextInt();
    }

    // Mostrar mensaje
    public static void showMessage(String message) {
        System.out.println(message);
    }
}
