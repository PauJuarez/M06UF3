package DAM.M06.uf3.mediavote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

public class Main {

	public static void main(String[] args) {
		Conexion.conectar();
		System.out.println("Connecting...");

		try {
			System.out.println("Connected!");

			while (true) {
				int option = View.showLoginMenu();
				switch (option) {
				case 1 -> {
					String email = View.showLoginForm();
					if (Model.loginUsuario(email)) {
						System.out.println("✅ Accés concedit!");
						while (true) {
							View.showMainMenu();
							int mainOption = new Scanner(System.in).nextInt();

							switch (mainOption) {
							case 1:
								System.out.println("Opción 1 seleccionada");
								View.showMediaList();
								List<Document> contenidosYVotos = Model.obtenerTodosLosContenidosYVotos();
								if (contenidosYVotos.isEmpty()) {
									System.out.println("❌ No hay contenidos disponibles.");
								} else {
									for (Document contenido : contenidosYVotos) {
										String titulo = contenido.getString("titulo");
										int votos = contenido.getInteger("votos");
										System.out.println("🎥 Título: " + titulo + " | Votos: " + votos);
									}
								}
								break;
							case 2:
								System.out.println("Opción 2 seleccionada");
								View.showVotingZone();
								break;
							case 3:
                                System.out.println("Introduce la fecha de inicio (yyyy-MM-dd): ");
                                Scanner scanner = new Scanner(System.in);
                                String startDateInput = scanner.nextLine();
                                System.out.println("Introduce la fecha de fin (yyyy-MM-dd): ");
                                String endDateInput = scanner.nextLine();

                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date startDate = dateFormat.parse(startDateInput);
                                    Date endDate = dateFormat.parse(endDateInput);
                                    Model.obtenerContenidosPorFechas(startDate, endDate);
                                } catch (Exception e) {
                                    System.out.println("❌ Error al parsear las fechas.");
                                }
                                break;
							case 4:
								System.out.println("👋 Saliendo...");
								Conexion.cerrarConexion();
								return;
							default:
								System.out.println("⚠️ Opción no válida. Intenta de nuevo.");
							}
						}
					} else {
						System.out.println("❌ Accés denegat!");
					}
				}
				case 2 -> {
					Usuario nuevoUsuario = View.showRegisterForm();
					if (Model.registrarUsuario(nuevoUsuario)) {
						System.out.println("✅ Registrat correctament!");
					} else {
						System.out.println("⚠️ No s'ha pogut registrar.");
					}
				}
				case 3 -> {
					View.showMessage("👋 Saliendo...");
					Conexion.cerrarConexion();
					return;
				}
				default -> View.showMessage("⚠️ Opció no vàlida. Intenta-ho de nou.");
				}
			}
		} catch (Exception e) {
			System.out.println("❌ Error al connectar amb la base de dades: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("👋 Adéu!");
		}
	}
}
