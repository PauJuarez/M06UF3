package DAM.M06.uf3.mediavote;

import org.bson.Document;
import java.util.Date;

public class Voto {
    private String usuario;
    private String titulo;
    private Date fechaVoto;

    // Constructor
    public Voto() {}

    public Voto(String usuario, String titulo, Date fechaVoto) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.fechaVoto = fechaVoto;
    }

    // Getters i Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaVoto() {
        return fechaVoto;
    }

    public void setFechaVoto(Date fechaVoto) {
        this.fechaVoto = fechaVoto;
    }

    // Convertir a Document per MongoDB
    public Document toDocument() {
        return new Document("usuario", usuario)
                .append("titulo", titulo)
                .append("fecha_voto", fechaVoto);
    }
}
