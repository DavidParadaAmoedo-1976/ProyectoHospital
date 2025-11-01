import Conexiones.ConexionMySQL;
import Conexiones.ConexionPostgreSQL;
import DAO.*;
import Modelo.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Programa {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n\nPulsa intro para ver menú");
            sc.nextLine();
            mostrarMenu();

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> crearEspecialidad();
                case 2 -> crearMedico();
                case 3 -> eliminarMedico();
                case 4 -> crearPaciente();
                case 5 -> eliminarPaciente();
                case 6 -> crearTratamiento();
                case 7 -> eliminarTratamientoPorNombre();
                case 8 -> listarTratamientosConPocosPacientes();
                case 9 -> listarCitasPorPaciente();
                case 10 -> obtenerCantidadTratamientosPorSala();
                case 11 -> listarTratamientosConEspecialidadYMedicos();
                case 12 -> obtenerPacientesPorEspecialidad();
                case 0 -> Salir();
            }
        }
    }

    private static void crearEspecialidad() {
        String nombreEspecialidad = ValidarDatos.leerNombre("especialidad");

        crearEspecialidad((nombreEspecialidad));
    }
    private static void crearEspecialidad(String nombreEspecialidad){
        EspecialidadesPostgreDAO especialidadesDAO = new EspecialidadesPostgreDAO();
        especialidadesDAO.crear(new EspecialidadesPostgre(nombreEspecialidad));
    }

    private static void crearMedico() {
        String nombreMedico = ValidarDatos.leerNombre("médico");
        String nombreContacto = ValidarDatos.leerNombre("contacto");
        String nif = ValidarDatos.leerDni();
        String telefono = ValidarDatos.leerTelefono();
        String email = ValidarDatos.leerEmail();

        crearMedico(nombreMedico, nombreContacto, nif, telefono, email);
    }

    private static void crearMedico(String nombreMedico,String nombreContacto, String nif, String telefono, String email){
        MedicosPostgreDAO medicoDAO = new MedicosPostgreDAO();
        medicoDAO.crear(new MedicosPostgre(nombreMedico, nombreContacto, nif, telefono, email));
    }

    private static void eliminarMedico() {
        if (mostrarMedicos()) return;
        int idMedico = ValidarDatos.enteroCorrecto("\nIntroduce el ID del medico: ", 1, Integer.MAX_VALUE);

        eliminarMedico(idMedico);
    }

    private static void eliminarMedico(int id){
        MedicosPostgreDAO medicoDAO = new MedicosPostgreDAO();
        medicoDAO.eliminar(id);
    }

    private static void crearPaciente() {
        String nombre = ValidarDatos.leerNombre("paciente");
        String email = ValidarDatos.leerEmail();
        System.out.println("Introduce la fecha de nacimiento con formato AAAA-MM-DD: ");
        LocalDate fecha = ValidarDatos.fechaCorrecta();

        crearPaciente(nombre, email, fecha);
    }

    private static void crearPaciente(String nombre, String email, LocalDate fecha){
        PacientesMySqlDAO pacientesMySqlDAO = new PacientesMySqlDAO();
        pacientesMySqlDAO.crear(new PacientesMySql(nombre, email, fecha));
    }

    private static void eliminarPaciente() {
        if (mostrarPacientes()) return;
        int idPaciente = ValidarDatos.enteroCorrecto("\nIntroduce el ID del paciente: ", 1, Integer.MAX_VALUE);

        eliminarPaciente(idPaciente);
    }
    private static void eliminarPaciente(int id){
        PacientesMySqlDAO pacientesMySqlDAO = new PacientesMySqlDAO();
        pacientesMySqlDAO.eliminar(id);
    }

    private static void crearTratamiento() {
        System.out.println("\n*** Crear nuevo tratamiento ***");

        System.out.print("Introduce el nombre del tratamiento: ");
        String nombre = sc.nextLine();

        System.out.print("Introduce la descripción del tratamiento: ");
        String descripcion = sc.nextLine();

        if (mostrarEspecialidades()) return;
        int idEspecialidad = ValidarDatos.enteroCorrecto("Elige el ID de la especialidad: ", 1, Integer.MAX_VALUE);

        mostrarMedicos();
        int idMedico = ValidarDatos.enteroCorrecto("Elige el ID del médico: ", 1, Integer.MAX_VALUE);

        crearTratamientoComun(nombre, descripcion, idEspecialidad, idMedico);
    }

    private static void crearTratamiento(String nombre, String descripcion, String nombreEspecialidad, String nifMedico) {
        System.out.println("\n*** Creando tratamiento automático ***");

        EspecialidadesPostgreDAO especialidadesDAO = new EspecialidadesPostgreDAO();
        MedicosPostgreDAO medicosDAO = new MedicosPostgreDAO();

        try {
            int idEspecialidad = especialidadesDAO.obtenerIdPorNombre(nombreEspecialidad);
            if (idEspecialidad == -1) {
                System.out.println("No existe la especialidad con nombre: " + nombreEspecialidad);
                return;
            }

            int idMedico = medicosDAO.obtenerIdPorNif(nifMedico);
            if (idMedico == -1) {
                System.out.println("No existe el médico con NIF: " + nifMedico);
                return;
            }

            crearTratamientoComun(nombre, descripcion, idEspecialidad, idMedico);
        } catch (Exception e) {
            System.err.println("Error al crear tratamiento automático: " + e.getMessage());
        }
    }

    private static void eliminarTratamientoPorNombre() {
        if (mostrarTratamientos()) return;
        String nombreTratamiento = ValidarDatos.leerNombre("tratamiento");
        eliminarTratamientoPorNombre(nombreTratamiento);
    }

    private static void eliminarTratamientoPorNombre(String nombre) {
        TratamientosMySqlDAO mySqlDAO = new TratamientosMySqlDAO();
        TratamientosPostgreDAO postgreDAO = new TratamientosPostgreDAO();

        int idTratamiento = mySqlDAO.obtenerIdPorNombre(nombre);

        if (idTratamiento != -1) {
            mySqlDAO.eliminar(idTratamiento);
            postgreDAO.eliminar(idTratamiento);
            System.out.println("Tratamiento eliminado correctamente de ambas bases de datos.");

        } else {
            System.out.println("No se encontró ningún tratamiento con el nombre " + nombre + ".");
        }
    }

    private static void listarTratamientosConPocosPacientes() {
        int numeroDePacientes = ValidarDatos.enteroCorrecto("Introduce el limite de pacientes para la busqueda: ",1,Integer.MAX_VALUE );
        listarTratamientosConPocosPacientes(numeroDePacientes);
    }

    private static void listarTratamientosConPocosPacientes(int cantidad) {
        PacientesTratamientosMySqlDAO pacientesTratamientosMySqlDAO = new PacientesTratamientosMySqlDAO();
        pacientesTratamientosMySqlDAO.tratamientoPorNumeroPacientes(cantidad);
    }

    private static void listarCitasPorPaciente() {
        CitasMySqlDAO.totalCitasPorPaciente();
    }

    private static void obtenerCantidadTratamientosPorSala() {
        SalasTratamientosPostgreDAO.listarTratamientosPorSala();
    }

    private static void listarTratamientosConEspecialidadYMedicos() {
        FuncionesCombinadasDAO.listarTratamientosConEspecialidadesYMedicos();
    }




    private static void obtenerPacientesPorEspecialidad() {
        System.out.println("PENDIENTE");
    }
    private static void obtenerPacientesPorEspecialidad(int idEspecialidad) {
        System.out.println("PENDIENTE");
    }

























    private static boolean mostrarPacientes() {
        PacientesMySqlDAO pacientesMySqlDAO = new PacientesMySqlDAO();
        List<PacientesMySql> pacientes = pacientesMySqlDAO.leerTodos();

        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes en la lista.");
            return true;
        }
        System.out.println("\n*** Pacientes ***\n");
        return false;
    }

    private static boolean mostrarEspecialidades() {
        EspecialidadesPostgreDAO especialidadesPostgreDAO = new EspecialidadesPostgreDAO();
        List<EspecialidadesPostgre> especialidades = especialidadesPostgreDAO.leerTodos();

        if (especialidades.isEmpty()) {
            System.out.println("No hay especialidades registradas.");
            return true;
        }

        System.out.println("\n*** Especialidades disponibles ***");
        for (EspecialidadesPostgre esp : especialidades) {
            System.out.println("ID.- " + esp.getId_especialidad() + " - " + esp.getNombre_especialidad());
        }
        return false;
    }

    private static boolean mostrarMedicos() {
        MedicosPostgreDAO medicosPostgreDAO = new MedicosPostgreDAO();
        List<MedicosPostgre> medicos = medicosPostgreDAO.leerTodos();
        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados.");
            return true;
        }

        System.out.println("\n*** Médicos disponibles ***");
        for (MedicosPostgre m : medicos) {
            System.out.println("ID.- " + m.getIdMedico() + " - " + m.getNombre() + " - " + m.getNombreContacto());
        }
        return false;
    }

    private static boolean mostrarTratamientos() {
        TratamientosMySqlDAO tratamientosMySqlDAO = new TratamientosMySqlDAO();
        List<TratamientosMySql> tratamientos = tratamientosMySqlDAO.leerTodos();
        if (tratamientos.isEmpty()) {
            System.out.println("No existe ningun tratamiento");
            return true;
        }
        System.out.println("\n*** Tratamientos disponibles ***");
        for (TratamientosMySql elemento : tratamientos) {
            System.out.println("ID.- " + elemento.getIdTratamiento() + " - " + elemento.getNombreTratamiento());
        }
        return false;
    }

    private static void crearTratamientoComun(String nombre, String descripcion, int idEspecialidad, int idMedico) {
        TratamientosPostgreDAO tratamientosPostgreDAO = new TratamientosPostgreDAO();
        TratamientosMySqlDAO tratamientosMySqlDAO = new TratamientosMySqlDAO();

        try {
            TratamientosPostgre tratamientoPostgre = new TratamientosPostgre(idMedico, idEspecialidad);
            int idTratamiento = tratamientosPostgreDAO.crear(tratamientoPostgre);

            if (idTratamiento != -1) {
                TratamientosMySql tratamientoMySql = new TratamientosMySql(idTratamiento, nombre, descripcion);
                tratamientosMySqlDAO.crear(tratamientoMySql);
                System.out.println("Tratamiento creado correctamente en ambas bases de datos.");
            } else {
                System.out.println("No se pudo crear el tratamiento en PostgreSQL.");
            }

        } catch (Exception e) {
            System.err.println("Error al crear tratamiento: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.print("\n\n\t\t\t===== MENU HOSPITAL =====\n" +
                "\n\t 1.- Crear una nueva especialidad médica.\n" +
                "\t 2.- Crear un nuevo médico.\n" +
                "\t 3.- Eliminar un médico por ID.\n" +
                "\t 4.- Crear un nuevo paciente.\n" +
                "\t 5.- Eliminar un paciente.\n" +
                "\t 6.- Crear nuevo tratamiento (nombre, descripción, especialidad, médico).\n" +
                "\t 7.- Eliminar un tratamiento por su nombre.\n" +
                "\t 8.- Listar tratamientos (menos de X pacientes asignados).\n" +
                "\t 9.- Obtener el total de citas realizadas por cada paciente.\n" +
                "\t10.- Obtener la cantidad de tratamientos por sala.\n" +
                "\t11.- Listar todos los tratamientos con sus respectivas especalidades y médicos (MySQL + PostgreSQL)\n" +
                "\t12.- Obtener todos los pacientes que han recibido un tratamiento de una especialidad dada (MySQL + PostgreSQL).\n" +
                "\t 0.- Salir.\n" +
                "\nElige una opción: ");
    }

    private static void Salir() {
        System.out.println("\nCerrando recursos...");

        sc.close();
        ValidarDatos.cerrarScanner();

        // Cerrar conexiones
        try {
            ConexionMySQL.getInstancia().cerrarConexion();
            ConexionPostgreSQL.getInstancia().cerrarConexion();
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexiones: " + e.getMessage());
        }

        System.out.println("Hasta luego!");
    }


}
