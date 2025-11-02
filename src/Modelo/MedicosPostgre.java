package Modelo;

public class MedicosPostgre {
    private int idMedico;
    private String nombre;
    private String nombreContacto;
    private String nif;
    private String telefono;
    private String email;

    public MedicosPostgre(int idMedico, String nombre, String nombreContacto, String nif, String telefono, String email) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.nombreContacto = nombreContacto;
        this.nif = nif;
        this.telefono = telefono;
        this.email = email;
    }

    public MedicosPostgre(String nombre, String nombre_contacto, String nif, String telefono, String email) {
        this.nombre = nombre;
        this.nombreContacto = nombre_contacto;
        this.nif = nif;
        this.telefono = telefono;
        this.email = email;
    }

    public MedicosPostgre(String nombre, String nif, String telefono, String email) {
        this.nombre = nombre;
        this.nif = nif;
        this.telefono = telefono;
        this.email = email;
    }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombre_contacto) {
        this.nombreContacto = nombre_contacto;
    }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return  "\nMédico" +
                "\nId: " + idMedico + ", Nombre: " + nombreContacto + ", NIF: " + nif +
                "\nTeléfono:" + telefono + ", Email: " + email;
    }
}

