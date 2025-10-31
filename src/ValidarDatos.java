import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ValidarDatos {
    private static final Scanner sc = new Scanner(System.in);

    public static String leerNombre(String persona) {
        String nombre = "";
        try {
            do {
                System.out.println("Introduzca el nombre de " + persona + " (mínimo 3 caracteres, solo letras y espacios):");
                nombre = sc.nextLine().trim();

                if (nombre.length() < 3) {
                    System.out.println("El nombre debe tener al menos 3 caracteres.");
                } else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ .]+")) {
                    System.out.println("El nombre solo puede contener letras y espacios.");
                }

            } while (nombre.length() < 3 || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ .]+"));
        } catch (Exception e) {
            System.out.println("Error al leer el nombre. Inténtelo nuevamente.");
        }
        return nombre;
    }

    public static String leerEmail() {
        String email = "";
        try {
            do {
                System.out.println("Introduzca Email:");
                email = sc.nextLine().trim();
            } while (email.length() > 100 || !email.matches("^[\\w.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        } catch (Exception e) {
            System.out.println("Error al leer el email. Inténtelo nuevamente.");
        }
        return email;
    }

    public static String leerDni() {
        String dni = "";
        try {
            do {
                System.out.println("Introduzca su DNI (8 números + 1 letra):");
                dni = sc.nextLine().trim();
            } while (!validarDni(dni));
        } catch (Exception e) {
            System.out.println("Error al leer el DNI. Inténtelo nuevamente.");
        }
        return dni;
    }
    private static boolean validarDni(String dni) {
        return dni != null && dni.matches("\\d{8}[A-Za-z]");
    }

    public static String leerTelefono() {
        String telefono = "";
        try {
            do {
                System.out.println("Introduzca su número de teléfono (puede incluir código de país):");
                telefono = sc.nextLine().trim();
            } while (!validarTelefono(telefono));
        } catch (Exception e) {
            System.out.println("Error al leer el teléfono. Inténtelo nuevamente.");
        }
        return telefono;
    }

    private static boolean validarTelefono(String telefono) {
        return telefono.matches("\\+?[0-9]{1,3}[ -]?\\d{9}") || telefono.matches("\\d{9}");
    }

    public static int enteroCorrecto(String dato, int minInclusive, int maxInclusive) {
        int num = 0;
        boolean datoOk = false;

        while (!datoOk) {
            System.out.print(dato);
            String input = sc.nextLine();
            try {
                num = Integer.parseInt(input);
                if (num >= minInclusive && num <= maxInclusive) {
                    datoOk = true;
                } else {
                    System.out.println("El valor debe estar entre " + minInclusive + " y " + maxInclusive);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduzca un número válido.");
            }
        }
        return num;
    }

    public static LocalDate fechaCorrecta() {
        Scanner sc = new Scanner(System.in);
        LocalDate fecha = null;
        boolean fechaOk = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate fechaMin = LocalDate.now().minusYears(150);
        LocalDate fechaMax = LocalDate.now();

        while (!fechaOk) {
            String input = sc.nextLine();
            try {
                fecha = LocalDate.parse(input, formatter);
                if (!fecha.isBefore(fechaMin) && !fecha.isAfter(fechaMax)) {
                    fechaOk = true;
                } else {
                    System.out.println("La fecha debe estar entre " + fechaMin + " y " + fechaMax);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error: Introduzca una fecha válida en formato aaaa-mm-dd.");
            }
        }
        return fecha;
    }

    public static void cerrarScanner() {
        try {
            System.out.println("Saliendo ...");
            sc.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar el scanner.");
        }
    }
}
