package DAM.M06.uf3.mediavote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ApiClient {
    private static final String BASE_URL = "https://apipauuf3m06.vercel.app/MediaRank";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Listar todos los usuarios");
            System.out.println("2. Contar votos por título");
            System.out.println("3. Obtener contenido por rango de fechas");
            System.out.println("4. Crear un usuario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next();
            }
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> listAll();
                case 2 -> countVotesByTitle();
                case 3 -> {
                    System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Ingrese la fecha de fin (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    getMediaByDateRange(startDate, endDate);
                }
                case 4 -> createUser(scanner);
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (option != 5);

        scanner.close();
    }

    public static void listAll() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());

            jsonArray.forEach(item -> {
                JSONObject jsonObject = (JSONObject) item;
                if (jsonObject.has("usuario")) {
                    JSONObject userJson = jsonObject.getJSONObject("usuario");
                    System.out.println("Usuario: " + userJson.optString("nombre", "N/A") + " " + userJson.optString("apellido", "N/A"));
                    System.out.println("Email: " + userJson.optString("email", "N/A") + "\n");
                } else {
                    System.out.println("No se encontró el usuario en el JSON.");
                }
            });
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
    }

    public static void countVotesByTitle() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/vote"))
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());

            jsonArray.forEach(item -> {
                JSONObject vote = (JSONObject) item;
                System.out.println(" - " + vote.getString("titulo") + ": " + vote.getInt("total_votos") + " votos");
            });
        } catch (Exception e) {
            System.err.println("Error al contar votos: " + e.getMessage());
        }
    }

    public static void getMediaByDateRange(String startDate, String endDate) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + startDate + "/" + endDate))
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONArray jsonArray = new JSONArray(response.body());
                System.out.println("📅 Contenido entre " + startDate + " y " + endDate + ":");
                jsonArray.forEach(item -> {
                    JSONObject content = (JSONObject) item;
                    System.out.println(" - " + content.getString("titulo") + " (Fecha: " + content.getString("fecha_subida") + ")");
                });
            } else {
                System.out.println("⚠️ Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener contenido por fechas: " + e.getMessage());
        }
    }

    public static void createUser(Scanner scanner) {
        try {
            System.out.print("Ingrese el nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Ingrese el email: ");
            String email = scanner.nextLine();

            JSONObject userJson = new JSONObject().put("usuario", new JSONObject()
                    .put("nombre", nombre)
                    .put("apellido", apellido)
                    .put("email", email));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(userJson.toString()))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Respuesta: " + response.body());
        } catch (Exception e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
        }
    }
}
