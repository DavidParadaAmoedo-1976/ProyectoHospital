package Modelo;

import java.time.LocalDate;

public class CitasMySql {
    private int idCita;
    private int idPaciente;
    private LocalDate fecha;



    public CitasMySql(int idCita, int idPaciente, LocalDate fecha) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return  "\nCita " +
                "\nId de la cita: " + idCita + ", Id del paciente: " + idPaciente + ", Fecha: " + fecha;
    }
}
