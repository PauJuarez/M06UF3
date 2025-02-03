package DAM.M06.uf3.mediavote;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class App {
    public static void main(String[] args) {
        // Tu URI de conexión MongoDB
        String url = "mongodb+srv://pau:pau@pau.2qrey.mongodb.net/?retryWrites=true&w=majority&appName=Pau";

        // Crear un objeto MongoClientURI con la URL proporcionada
        MongoClientURI uri = new MongoClientURI(url);

        // Conectar a MongoDB usando el URI
        try (MongoClient mongoClient = new MongoClient(uri)) {
            // Obtener la base de datos
            MongoDatabase database = mongoClient.getDatabase("test"); // Reemplaza "test" con el nombre de tu base de datos

            System.out.println("Conexión exitosa a la base de datos: " + database.getName());
            
            // Puedes realizar operaciones en la base de datos aquí
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}
