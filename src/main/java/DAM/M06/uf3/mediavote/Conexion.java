package DAM.M06.uf3.mediavote;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Conexion {

    private static final String URI = "mongodb+srv://pau:pau@pau.2qrey.mongodb.net/?retryWrites=true&w=majority&appName=Pau";
    private static MongoClient mongoClient;

    // Método para conectar a la base de datos
    public static void conectar() {
        try {
            mongoClient = MongoClients.create(URI);
            System.out.println("Conectado a MongoDB");
        } catch (MongoException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    // Método para obtener el cliente de MongoDB
    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión cerrada.");
        }
    }
}
