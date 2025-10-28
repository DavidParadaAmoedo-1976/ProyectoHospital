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
            mostrarMenu();

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> crearEspecialidad();
                case 2 -> crearMedico();
                case 3 -> eliminarMedicoPorId();
                case 4 -> crearPaciente();
                case 5 -> eliminarPacientePorId();
                case 6 -> crearTratamiento();
                case 7 -> eliminarTratamientoPorNombre();
                case 8 -> listarTratamientoPorMaximoDePacientes();
                case 9 -> listarCitasPorPaciente();
                case 10 -> listarTratamientosPorSala();
                case 11 -> listarTratamientosConEspecialidadesYMedicos();
                case 12 -> ListarPacientesPorTratamientoYEspecialidad();
                case 0 -> Salir();
            }
        }
    }

    private static void ListarPacientesPorTratamientoYEspecialidad() {
        System.out.println("PENDIENTE");
    }

    private static void listarTratamientosConEspecialidadesYMedicos() {
        System.out.println("PENDIENTE");
    }

    private static void listarTratamientosPorSala() {
        System.out.println("PENDIENTE");
    }

    private static void listarCitasPorPaciente() {
        System.out.println("PENDIENTE");
    }

    private static void listarTratamientoPorMaximoDePacientes() {
        System.out.println("PENDIENTE");
    }

    private static void eliminarTratamientoPorNombre() {
        if (mostrarTratamientos()) return;
        String nombreTratamiento = ValidarDatos.leerNombre("tratamiento");
        TratamientosMySqlDAO mySqlDAO = new TratamientosMySqlDAO();
        TratamientosPostgreDAO postgreDAO = new TratamientosPostgreDAO();

        int idTratamiento = mySqlDAO.obtenerIdPorNombre(nombreTratamiento);

        if (idTratamiento != -1) {
            mySqlDAO.eliminar(idTratamiento);
            postgreDAO.eliminar(idTratamiento);
            System.out.println("Tratamiento eliminado correctamente de ambas bases de datos.");


        } else {
            System.out.println("No se encontró ningún tratamiento con el nombre " + nombreTratamiento + ".");
        }
    }

    private static void crearTratamiento() {
        System.out.println("\n*** Crear nuevo tratamiento ***");

        System.out.print("Introduce el nombre del tratamiento: ");
        String nombreTratamiento = sc.nextLine();

        System.out.print("Introduce la descripción del tratamiento: ");
        String descripcion = sc.nextLine();

        if (mostrarEspecialidades()) return;

        int idEspecialidad = ValidarDatos.enteroCorrecto("Elige el ID de la especialidad: ", 1, Integer.MAX_VALUE);


        mostrarMedicos();

        int idMedico = ValidarDatos.enteroCorrecto("Elige el ID del médico: ", 1, Integer.MAX_VALUE);

        TratamientosPostgreDAO tratamientosPostgreDAO = new TratamientosPostgreDAO();
        TratamientosMySqlDAO tratamientosMySqlDAO = new TratamientosMySqlDAO();

        try {
            TratamientosPostgre tratamiento = new TratamientosPostgre(idEspecialidad, idMedico);
            int idTratamiento = tratamientosPostgreDAO.obtenerId(tratamiento);

            if (idTratamiento != -1) {
                TratamientosMySql nuevoTratamiento = new TratamientosMySql(idTratamiento, nombreTratamiento, descripcion);
                tratamientosMySqlDAO.crear(nuevoTratamiento);
                System.out.println("Tratamiento creado correctamente en ambas bases de datos.");
            } else {
                System.out.println("No se pudo crear el tratamiento en MySQL.");
            }
        } catch (Exception e) {
            System.err.println("Error al crear tratamiento: " + e.getMessage());
        }
    }

    private static void eliminarPacientePorId() {
        if (mostrarPacientes()) return;
        int idPaciente = ValidarDatos.enteroCorrecto("\nIntroduce el ID del paciente: ", 1, Integer.MAX_VALUE);

        PacientesMySqlDAO pacientesMySqlDAO = new PacientesMySqlDAO();
        pacientesMySqlDAO.eliminar(idPaciente);
    }

    private static void crearPaciente() {
        String nombre = ValidarDatos.leerNombre("paciente");
        System.out.println("Introduce el email: ");
        String email = ValidarDatos.leerEmail();
        System.out.println("Introduce la fecha de nacimiento con formato AAAA-MM-DD: ");
        LocalDate fecha = ValidarDatos.fechaCorrecta();

        PacientesMySqlDAO pacientesMySqlDAO = new PacientesMySqlDAO();
        pacientesMySqlDAO.crear(new PacientesMySql(nombre, email, fecha));
    }

    private static void eliminarMedicoPorId() {
        if (mostrarMedicos()) return;

        int idMedico = ValidarDatos.enteroCorrecto("\nIntroduce el ID del medico: ", 1, Integer.MAX_VALUE);

        MedicosPostgreDAO medicoDAO = new MedicosPostgreDAO();
        medicoDAO.eliminar(idMedico);
    }

    private static void crearMedico() {
        String nombreMedico = ValidarDatos.leerNombre("médico");
        String nombreContacto = ValidarDatos.leerNombre("contacto");
        String nif = ValidarDatos.leerDni();
        String telefono = ValidarDatos.leerTelefono();
        String email = ValidarDatos.leerEmail();

        MedicosPostgreDAO medicoDAO = new MedicosPostgreDAO();
        medicoDAO.crear(new MedicosPostgre(nombreMedico, nombreContacto, nif, telefono, email));
    }

    private static void crearEspecialidad() {
        String nombreEspecialidad = ValidarDatos.leerNombre("especialidad");

        EspecialidadesPostgreDAO especialidadesDAO = new EspecialidadesPostgreDAO();
        especialidadesDAO.crear(new EspecialidadesPostgre(nombreEspecialidad));
    }

    private static void mostrarMenu() {
        System.out.print("\n\t\t\t===== MENU HOSPITAL =====\n" +
                "\n\t 1.- Crear una nueva especialidad médica.\n" +
                "\t 2.- Crear un nuevo médico.\n" +
                "\t 3.- Eliminar un médico por ID.\n" +
                "\t 4.- Crear un nuevo paciente.\n" +
                "\t 5.- Eliminar un paciente     *** preguntar que opción ***\n" +
                "\t 6.- Crear nuevo tratamiento (nombre, descripción, especialidad, médico).\n" +
                "\t 7.- Eliminar un tratamiento por su nombre (MySQL + PostgreSQL)\n" +
                "\t 8.- Listar tratamientos (menos de X pacientes asignados) (MySQL)\n" +
                "\t 9.- Obtener el total de citas realizadas por cada paciente (MySQL)\n" +
                "\t10.- Obtener la cantidad de tratamientos por sala (PostgreSQL)\n" +
                "\t11.- Listar todos los tratamientos con sus respectivas especalidades y médicos (MySQL + PostgreSQL)\n" +
                "\t12.- Obtener todos los pacientes que han recibido un tratamiento de una especialidad dada (MySQL + PostgreSQL).\n" +
                "\t 0.- Salir.\n" +
                "\nElige una opción: ");
    }

    private static boolean mostrarPacientes() {
        PacientesMySqlDAO pacientesMySqlDAO = new PacientesMySqlDAO();
        List<PacientesMySql> pacientes = pacientesMySqlDAO.leerTodos();

        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes en la lista.");
            return true;
        }
        System.out.println("\n*** Pacientes ***\n");
        paginar(pacientes);
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

    private static void paginar(List lista) {
        final int ELEMENTOS_POR_PAGINA = 10;
        int pagina = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            int inicio = pagina * ELEMENTOS_POR_PAGINA;
            int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA, lista.size());

            System.out.println("\nMostrando elementos desde " + (inicio + 1) + " a " + fin + ":");
            for (int i = inicio; i < fin; i++) {
                System.out.println(lista.get(i));
            }

            if (fin == lista.size()) {
                System.out.println("\nFin de la lista.");
                break;
            }

            System.out.print("\nPresiona ENTER para ver más o escribe 'S' para salir: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("S")) break;

            pagina++;
        }
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
