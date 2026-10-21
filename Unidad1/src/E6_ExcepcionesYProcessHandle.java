import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class E6_ExcepcionesYProcessHandle {
    public static void main(String[] args) {
        System.out.println("--- 1. PRUEBA DE GESTIÓN DE EXCEPCIONES ---");
        try {
            // Intentamos ejecutar un comando que no existe en el sistema
            ProcessBuilder pbError = new ProcessBuilder("comando_que_no_existe_12345");
            Process p = pbError.start();
            p.waitFor();
        } catch (IOException e) {
            System.out.println("-> IOException capturada correctamente: No se pudo encontrar o ejecutar el programa.");
            System.out.println("   Detalle técnico: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("-> InterruptedException capturada.");
            Thread.currentThread().interrupt();
        }

        System.out.println("\n--- 2. USO DE LA PROCESS API MODERNA (ProcessHandle) ---");
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pbValido = new ProcessBuilder();

        if (os.contains("win")) {
            pbValido.command("ping", "-n", "5", "127.0.0.1");
        } else {
            pbValido.command("ping", "-c", "5", "127.0.0.1");
        }

        try {
            Process proceso = pbValido.start();

            // Obtenemos la referencia ProcessHandle (Java 9+)
            ProcessHandle handle = proceso.toHandle();

            System.out.println("PID del proceso en el SO: " + handle.pid());
            System.out.println("Usuario propietario:      " + handle.info().user().orElse("Desconocido"));
            System.out.println("Hora de inicio:           " + handle.info().startInstant().orElse(Instant.now()));

            proceso.waitFor(2, TimeUnit.SECONDS);
            if (proceso.isAlive()) {
                System.out.println("Cancelando proceso mediante ProcessHandle...");
                handle.destroy(); // Cancelación a través del handle
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error durante la prueba de ProcessHandle: " + e.getMessage());
        }
    }
}