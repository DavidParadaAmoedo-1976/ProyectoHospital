package Modelo;

import java.time.LocalDate;

public class CitasMySql {
    private int id_cita;
    private int id_paciente;
    private LocalDate fecha;

    public CitasMySql() {
    }

    public CitasMySql(int id_cita, int id_paciente, LocalDate fecha) {
        this.id_cita = id_cita;
        this.id_paciente = id_paciente;
        this.fecha = fecha;
    }

    public int getId_cita() {
        return id_cita;
    }

    public void setId_cita(int id_cita) {
        this.id_cita = id_cita;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
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
                "\nId de la cita: " + id_cita + ", Id del paciente: " + id_paciente + ", Fecha: " + fecha;
    }
}
