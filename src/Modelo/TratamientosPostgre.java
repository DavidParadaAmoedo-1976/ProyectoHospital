package Modelo;

public class TratamientosPostgre {
    private int idTratamiento;
    private int idMedico;
    private int idEspecialidad;

    public TratamientosPostgre(int idMedico, int idEspecialidad) {
        this.idMedico = idMedico;
        this.idEspecialidad = idEspecialidad;
    }

    public TratamientosPostgre(int idTratamiento, int idMedico, int idEspecialidad) {
        this.idTratamiento = idTratamiento;
        this.idMedico = idMedico;
        this.idEspecialidad = idEspecialidad;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    @Override
    public String toString() {
        return  "Id del Tratamiento.- " + idTratamiento + ", Id del Medico.- " + idMedico + ", Id de la Especialidad.- " + idEspecialidad;
    }
}
