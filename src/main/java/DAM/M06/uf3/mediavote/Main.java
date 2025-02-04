package DAM.M06.uf3.mediavote;

public class Main {

	public static void main(String[] args) {
        Conexion.conectar();
		System.out.println("Connecting...");
		try {
			System.out.println("Connected!");

			while (true) {
				int option = View.showLoginMenu();
				switch (option) {
				case 1 -> System.out.println("1");
				case 2 -> System.out.println("2");
				case 3 -> {
					View.showMessage("Saliendo...");
					return;
				}
				default -> View.showMessage("Opción no válida. Intente nuevamente.");
				}
			}
		} catch (Exception e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("Adue");
		}
	}
}

