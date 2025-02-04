package DAM.M06.uf3.mediavote;

import org.bson.Document;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;

public class Model {

    // Método para obtener documentos de la colección
    public static List<Document> obtenerDocumentos() {
        List<Document> documentos = new ArrayList<>();
        try {
            MongoDatabase database = Conexion.getMongoClient().getDatabase("MediaRankDB");
            MongoCollection<Document> collection = database.getCollection("MediaRank");

            // Obtener todos los documentos
            FindIterable<Document> docs = collection.find();
            for (Document doc : docs) {
                documentos.add(doc);
            }
        } catch (MongoException e) {
            System.err.println("Error al obtener documentos: " + e.getMessage());
        }
        return documentos;
    }

    public static void main(String[] args) {
        // Conectar a la base de datos
        Conexion.conectar();

        // Obtener documentos
        List<Document> documentos = obtenerDocumentos();
        for (Document doc : documentos) {
            System.out.println(doc.toJson());
        }

        // Cerrar la conexión
        Conexion.cerrarConexion();
    }
}
