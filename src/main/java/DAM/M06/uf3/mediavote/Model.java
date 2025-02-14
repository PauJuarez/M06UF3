package DAM.M06.uf3.mediavote;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Model {

    private static MongoCollection<Document> getCollection() {
        MongoDatabase database = Conexion.getMongoClient().getDatabase("MediaRankDB");
        return database.getCollection("MediaRank");
    }

    public static boolean registrarUsuario(Usuario usuario) {
        MongoCollection<Document> collection = getCollection();

        // Verificar si el usuario ya existe
        if (collection.find(eq("usuario.email", usuario.getEmail())).first() != null) {
            System.out.println("⚠️ ¡El usuario ya existe en MediaRank!");
            return false;
        }

        // Crear y guardar nuevo usuario
        Document userDoc = new Document("usuario", new Document("nombre", usuario.getNombre())
                .append("apellido", usuario.getApellido())
                .append("email", usuario.getEmail()))
                .append("contenido", Arrays.asList())
                .append("votos", Arrays.asList());
        
        collection.insertOne(userDoc);
        System.out.println("✅ Usuario registrado correctamente.");
        return true;
    }

    public static boolean loginUsuario(String email) {
        MongoCollection<Document> collection = getCollection();
        Document userDoc = collection.find(eq("usuario.email", email)).first();

        if (userDoc != null) {
            System.out.println("✅ Login correcto! Bienvenido, " + ((Document) userDoc.get("usuario")).getString("nombre"));
            return true;
        }
        System.out.println("❌ Usuario no encontrado!");
        return false;
    }
    
    public static List<Document> obtenerTodosLosContenidosYVotos() {
        MongoCollection<Document> collection = getCollection();
        List<Document> usuarios = collection.find().into(new java.util.ArrayList<>());
        List<Document> contenidosConVotos = new java.util.ArrayList<>();

        for (Document usuario : usuarios) {
            List<Document> contenidos = (List<Document>) usuario.get("contenido");
            List<Document> votos = (List<Document>) usuario.get("votos");

            for (Document contenido : contenidos) {
                String titulo = contenido.getString("titulo");
                if (contenidosConVotos.stream().noneMatch(c -> c.getString("titulo").equals(titulo))) {
                    contenidosConVotos.add(new Document("titulo", titulo).append("votos", 0));
                }
            }

            for (Document voto : votos) {
                String titulo = voto.getString("titulo");
                contenidosConVotos.stream()
                        .filter(c -> c.getString("titulo").equals(titulo))
                        .forEach(c -> c.put("votos", c.getInteger("votos") + 1));
            }
        }
        return contenidosConVotos;
    }

    public static void obtenerContenidosPorFechas(Date startDate, Date endDate) {
        MongoCollection<Document> collection = getCollection();
        List<Document> usuarios = collection.find().into(new java.util.ArrayList<>());
        
        System.out.println("📋 Contenidos entre " + startDate + " y " + endDate + ":");
        
        for (Document usuario : usuarios) {
            List<Document> contenidos = (List<Document>) usuario.get("contenido");
            
            contenidos.stream()
                .filter(c -> {
                    Date fecha = c.getDate("fecha_subida");
                    return (fecha.after(startDate) || fecha.equals(startDate)) && 
                           (fecha.before(endDate) || fecha.equals(endDate));
                })
                .forEach(c -> System.out.println("🎥 Título: " + c.getString("titulo") + " | Fecha Subida: " + c.getDate("fecha_subida")));
        }
    }
}
