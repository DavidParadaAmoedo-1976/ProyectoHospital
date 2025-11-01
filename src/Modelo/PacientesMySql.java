package Modelo;

import java.time.LocalDate;

public class PacientesMySql {
    private int idPaciente;
    private String nombre;
    private String email;
    private LocalDate fechaNacimiento;

    public PacientesMySql(String nombre, String email, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
    }

    public PacientesMySql(int idPaciente, String nombre, String email, LocalDate fechaNacimiento) {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return  "Id.- " + idPaciente + ", " + nombre + ", " + fechaNacimiento;
    }
}

