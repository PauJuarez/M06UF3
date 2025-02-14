package DAM.M06.uf3.mediavote;

import org.bson.Document;
import java.util.List;

public class UsuarioMedia {
    private Usuario usuario;
    private List<Contenido> contenido;
    private List<Voto> votos;

    // Constructor
    public UsuarioMedia() {}

    public UsuarioMedia(Usuario usuario, List<Contenido> contenido, List<Voto> votos) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.votos = votos;
    }

    // Getters i Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Contenido> getContenido() {
        return contenido;
    }

    public void setContenido(List<Contenido> contenido) {
        this.contenido = contenido;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    // Convertir a Document per MongoDB
    public Document toDocument() {
        return new Document("usuario", new Document("nombre", usuario.getNombre())
                .append("apellido", usuario.getApellido())
                .append("email", usuario.getEmail()))
                .append("contenido", contenido.stream().map(Contenido::toDocument).toList())
                .append("votos", votos.stream().map(Voto::toDocument).toList());
    }
}
