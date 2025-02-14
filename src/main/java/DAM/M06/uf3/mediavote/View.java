package DAM.M06.uf3.mediavote;

import java.util.Scanner;

public class View {

    private static final Scanner scanner = new Scanner(System.in);

    // 📌 Mostrar menú de login i registre
    public static int showLoginMenu() {
        System.out.println("\n==== 📺 MEDIA VOTE ====");
        System.out.println("1. 🔹 Login");
        System.out.println("2. 📝 Registre");
        System.out.println("3. ❌ Sortir");
        System.out.print("➡️ Opció: ");
        return scanner.nextInt();
    }

    // 📌 Demanar dades per registrar usuari
    public static Usuario showRegisterForm() {
        scanner.nextLine(); // Netejar buffer
        System.out.print("🔹 Nom: ");
        String nombre = scanner.nextLine();
        System.out.print("🔹 Cognom: ");
        String apellido = scanner.nextLine();
        System.out.print("🔹 Email: ");
        String email = scanner.nextLine();

        return new Usuario(nombre, apellido, email);
    }

    // 📌 Demanar dades per login
    public static String showLoginForm() {
        scanner.nextLine(); // Netejar buffer
        System.out.print("🔹 Email: ");
        return scanner.nextLine();
    }

    // 📌 Mostrar menú principal después del login
    public static void showMainMenu() {
        System.out.println("\n==== MENU PRINCIPAL ====");
        System.out.println("1. Ver listado de media con votos");
        System.out.println("2. Zona de votación");
        System.out.println("3. Buscar media por fecha");
        System.out.println("4. Salir");
        System.out.print("➡️ Elige una opción: ");
    }

    public static void showMediaList() {
        System.out.println("\n==== 📜 LISTADO DE MEDIA CON VOTOS ====");
    }

    public static void showVotingZone() {
        System.out.println("\n==== 🗳️ ZONA DE VOTACIÓN ====");
    }

    public static void showSearchMediaByDateMenu() {
        System.out.println("\n==== 🔍 BÚSQUEDA DE MEDIA POR FECHA ====");
        System.out.print("➡️ Introduce la fecha (YYYY-MM-DD): ");
    }

    public static void showSearchResults() {
        System.out.println("\n==== 🎬 RESULTADOS DE LA BÚSQUEDA ====");
    }

    
    // 📌 Mostrar missatge
    public static void showMessage(String message) {
        System.out.println(message);
    }
    
    
}
