package Modelo;

import java.time.LocalDate;

public class PacientesTratamientosMySql {
    private int idPaciente;
    private int idTratamiento;
    private LocalDate fechaInicio;

    public PacientesTratamientosMySql(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public PacientesTratamientosMySql(int idPaciente, int idTratamiento, LocalDate fechaInicio) {
        this.idPaciente = idPaciente;
        this.idTratamiento = idTratamiento;
        this.fechaInicio = fechaInicio;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String toString() {
        return  "Id del Paciente.- " + idPaciente + ", Id del Tratamiento.- " + idTratamiento +", fechaInicio: " + fechaInicio;
    }
}
