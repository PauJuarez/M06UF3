package DAM.M06.uf3.mediavote;

import com.mongodb.*;
import com.mongodb.client.*;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        // URI 
        String uri = "mongodb+srv://pau:pau@pau.2qrey.mongodb.net/?retryWrites=true&w=majority&appName=Pau";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            String databaseName = "MediaRankDB"; 
            String collectionName = "MediaRank";

            // Obtener base de datos y colección
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            FindIterable<Document> documents = collection.find();

            System.out.println("Documentos en la colección " + collectionName + ":");
            for (Document doc : documents) {
                System.out.println(doc.toJson());
            }

        } catch (MongoException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}
