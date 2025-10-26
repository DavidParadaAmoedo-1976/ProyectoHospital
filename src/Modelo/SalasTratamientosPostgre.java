package Modelo;

public class SalasTratamientosPostgre {
    private int idSala;
    private int idTratamiento;

    public SalasTratamientosPostgre() {
    }

    public SalasTratamientosPostgre(int idSala, int idTratamiento) {
        this.idSala = idSala;
        this.idTratamiento = idTratamiento;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    @Override
    public String toString() {
        return "SalasTratamientosPostgre{" +
                "idSala=" + idSala +
                ", idTratamiento=" + idTratamiento +
                '}';
    }
}
