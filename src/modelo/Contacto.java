package modelo;

public class Contacto {
    private int idContacto;
    private String nombre;
    private String telefono;
    private String correo;

    public Contacto() {}

    public Contacto(int idContacto, String nombre, String telefono, String correo) {
        this.idContacto = idContacto;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getIdContacto() { return idContacto; }
    public void setIdContacto(int idContacto) { this.idContacto = idContacto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
