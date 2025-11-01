package Modelo;

public class TratamientosMySql {
    private int idTratamiento;
    private String nombreTratamiento;
    private String descripcion;

    public TratamientosMySql(String nombreTratamiento, String descripcion) {
        this.nombreTratamiento = nombreTratamiento;
        this.descripcion = descripcion;
    }

    public TratamientosMySql(int idTratamiento, String nombreTratamiento, String descripcion) {
        this.idTratamiento = idTratamiento;
        this.nombreTratamiento = nombreTratamiento;
        this.descripcion = descripcion;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    @Override
    public String toString() {
        return  "Id del Tratamiento.- " + idTratamiento + ", Nombre del Tratamiento.- " + nombreTratamiento + ", Descripcion: " + descripcion;
    }
}
