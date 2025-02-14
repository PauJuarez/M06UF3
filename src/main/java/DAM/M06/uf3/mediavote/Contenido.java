package DAM.M06.uf3.mediavote;

import org.bson.Document;
import java.util.Date;
import java.util.List;

public class Contenido {
    private String url;
    private List<String> generos;
    private String titulo;
    private String tipo;
    private Date fechaSubida;

    // Constructor
    public Contenido() {}

    public Contenido(String url, List<String> generos, String titulo, String tipo, Date fechaSubida) {
        this.url = url;
        this.generos = generos;
        this.titulo = titulo;
        this.tipo = tipo;
        this.fechaSubida = fechaSubida;
    }

    // Getters i Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    // Convertir a Document per MongoDB
    public Document toDocument() {
        return new Document("url", url)
                .append("generos", generos)
                .append("titulo", titulo)
                .append("tipo", tipo)
                .append("fecha_subida", fechaSubida);
    }
}
