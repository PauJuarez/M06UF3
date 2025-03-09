package DAM.M06.uf3.mediavote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ApiClient {

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
            option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    listAll();
                    break;
                case 2:
                    countVotesByTitle();
                    break;
                case 3:
                    System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Ingrese la fecha de fin (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    getMediaByDateRange(startDate, endDate);
                    break;
                case 4:
                    createUser(scanner);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (option != 5);

        scanner.close();
    }

    public static void listAll() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://apipauuf3m06.vercel.app/MediaRank"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            System.out.println(responseBody);
            System.out.println(".........");

            JSONArray jsonArray = new JSONArray(responseBody);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.has("usuario")) {
                    JSONObject userJson = jsonObject.getJSONObject("usuario");

                    User user = User.fromJson(userJson);
                    System.out.println("Usuario: " + user.getNombre() + " " + user.getApellido());
                    System.out.println("Email: " + user.getEmail() + "\n");
                } else {
                    System.out.println("No se encontró el usuario en el JSON.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void countVotesByTitle() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://apipauuf3m06.vercel.app/MediaRank"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            Map<String, Integer> voteCountByTitle = new HashMap<>();

            JSONArray jsonArray = new JSONArray(responseBody);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.has("votos")) {
                    JSONArray votosArray = jsonObject.getJSONArray("votos");
                    for (int j = 0; j < votosArray.length(); j++) {
                        JSONObject votoJson = votosArray.getJSONObject(j);
                        String titulo = votoJson.getString("titulo");

                        voteCountByTitle.put(titulo, voteCountByTitle.getOrDefault(titulo, 0) + 1);
                    }
                }
            }

            System.out.println("Cantidad de votos por título:");
            for (Map.Entry<String, Integer> entry : voteCountByTitle.entrySet()) {
                System.out.println(" - " + entry.getKey() + ": " + entry.getValue() + " votos");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getMediaByDateRange(String startDate, String endDate) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = "https://apipauuf3m06.vercel.app/MediaRank/" + startDate + "/" + endDate;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                JSONArray jsonArray = new JSONArray(responseBody);

                System.out.println("📅 Contenido entre " + startDate + " y " + endDate + ":");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    String titulo = item.getString("titulo");
                    String fechaSubida = item.getString("fecha_subida");
                    System.out.println(" - " + titulo + " (Fecha: " + fechaSubida + ")");
                }
            } else {
                System.out.println("⚠️ Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
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

            JSONObject userJson = new JSONObject();
            userJson.put("usuario", new JSONObject()
                    .put("nombre", nombre)
                    .put("apellido", apellido)
                    .put("email", email));

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://apipauuf3m06.vercel.app/MediaRank"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(userJson.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Respuesta: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
