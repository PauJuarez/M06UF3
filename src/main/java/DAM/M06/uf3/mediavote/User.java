package DAM.M06.uf3.mediavote;

import org.json.JSONObject;

public class User {
    private String nombre;
    private String apellido;
    private String email;

    public User() {}

    public User(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", this.nombre);
        jsonObject.put("apellido", this.apellido);
        jsonObject.put("email", this.email);
        return jsonObject.toString();
    }

    public static User fromJson(JSONObject jsonObject) {
        String nombre = jsonObject.getString("nombre");
        String apellido = jsonObject.getString("apellido");
        String email = jsonObject.getString("email");

        return new User(nombre, apellido, email);
    }
}
