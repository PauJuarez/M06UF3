package DAM.M06.uf3.mediavote;

import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Voto {
    private String usuario;
    private String titulo;
    private Date fechaVoto;

    public Voto() {}

    public Voto(String usuario, String titulo, Date fechaVoto) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.fechaVoto = fechaVoto;
    }

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

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("usuario", this.usuario);
        jsonObject.put("titulo", this.titulo);
        jsonObject.put("fecha_voto", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(this.fechaVoto));
        return jsonObject.toString();
    }

    public static Voto fromJson(JSONObject jsonObject) {
        String usuario = jsonObject.getString("usuario");
        String titulo = jsonObject.getString("titulo");
        String fechaStr = jsonObject.getString("fecha_voto");

        Date fechaVoto = null;
        try {
            fechaVoto = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(fechaStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Voto(usuario, titulo, fechaVoto);
    }
}
