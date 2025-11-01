package Modelo;

public class EspecialidadesPostgre {
    private int id_especialidad;
    private String nombre_especialidad;



    public EspecialidadesPostgre(String nombre_especialidad) {
        this.nombre_especialidad = nombre_especialidad;
    }

    public EspecialidadesPostgre(int id_especialidad, String nombre_especialidad) {
        this.id_especialidad = id_especialidad;
        this.nombre_especialidad = nombre_especialidad;
    }

    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public String getNombre_especialidad() {
        return nombre_especialidad;
    }

    public void setNombre_especialidad(String nombre_especialidad) {
        this.nombre_especialidad = nombre_especialidad;
    }

    @Override
    public String toString() {
        return  "\nEspecialidad" +
                "\nId de la especialidad:" + id_especialidad + " Nombre de la especialidad: " + nombre_especialidad;
    }
}
