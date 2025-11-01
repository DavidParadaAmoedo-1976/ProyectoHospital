import Conexiones.ConexionMySQL;
import Conexiones.ConexionPostgreSQL;
import DAO.*;
import Modelo.*;
import java.sql.SQLException;
import java.time.LocalDate;
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
                case 9 -> obtenerCitasPorPaciente();
                case 10 -> obtenerCantidadTratamientosPorSala();
                case 11 -> listarTratamientosConEspecialidadYMedico();
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
        MedicosPostgreDAO.leerTodosReducido();
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
        PacientesMySqlDAO.leerTodosReducido();
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

        EspecialidadesPostgreDAO.leerTodos();
        int idEspecialidad = ValidarDatos.enteroCorrecto("Elige el ID de la especialidad: ", 1, Integer.MAX_VALUE);

        MedicosPostgreDAO.leerTodosReducido();
        int idMedico = ValidarDatos.enteroCorrecto("Elige el ID del médico: ", 1, Integer.MAX_VALUE);

        crearTratamientoComun(nombre, descripcion, idEspecialidad, idMedico);
    }

    private static void crearTratamiento(String nombre, String descripcion, String nombreEspecialidad, String nifMedico) {
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
        TratamientosMySqlDAO.leerTodosReducido();
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

    private static void obtenerCitasPorPaciente() {
        CitasMySqlDAO.totalCitasPorPaciente();
    }

    private static void obtenerCantidadTratamientosPorSala() {
        SalasTratamientosPostgreDAO.listarTratamientosPorSala();
    }

    private static void listarTratamientosConEspecialidadYMedico() {
        FuncionesCombinadasDAO.listarTratamientosConEspecialidadesYMedicos();
    }

    private static void obtenerPacientesPorEspecialidad() {
        EspecialidadesPostgreDAO.leerTodos();
        int idEspecialidad = ValidarDatos.enteroCorrecto("\nSelecciona la especialidad: ",1 , Integer.MAX_VALUE);
        obtenerPacientesPorEspecialidad(idEspecialidad);
    }
    private static void obtenerPacientesPorEspecialidad(int idEspecialidad) {
        TratamientosPostgreDAO.obtenerTratamientoPorEspecialidad(idEspecialidad);
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
        System.out.print("""
                \t\t\t===== MENU HOSPITAL =====
                
                \t 1.- Crear una nueva especialidad médica.
                \t 2.- Crear un nuevo médico.
                \t 3.- Eliminar un médico por ID.
                \t 4.- Crear un nuevo paciente.
                \t 5.- Eliminar un paciente.
                \t 6.- Crear nuevo tratamiento.
                \t 7.- Eliminar un tratamiento por su nombre.
                \t 8.- Listar tratamientos (menos de X pacientes asignados).
                \t 9.- Obtener el total de citas realizadas por cada paciente.
                \t10.- Obtener la cantidad de tratamientos por sala.
                \t11.- Listar todos los tratamientos con sus respectivas especalidades y médicos.
                \t12.- Obtener todos los pacientes que han recibido un tratamiento de una especialidad dada.
                \t 0.- Salir.
                
                Elige una opción:\s""");
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
