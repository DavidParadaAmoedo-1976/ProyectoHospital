package Modelo;

public class SalasPostgre {
    private int idSala;
    private  String nombreSala;
    private  String ubicacion;

    public SalasPostgre(String nombreSala, String ubicacion) {
        this.nombreSala = nombreSala;
        this.ubicacion = ubicacion;
    }

    public SalasPostgre(int idSala, String nombreSala, String ubicacion) {
        this.idSala = idSala;
        this.nombreSala = nombreSala;
        this.ubicacion = ubicacion;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Id de la Sala.- " + idSala + ", Nombre de la Sala: " + nombreSala + ", Ubicación: " + ubicacion;
    }
}
