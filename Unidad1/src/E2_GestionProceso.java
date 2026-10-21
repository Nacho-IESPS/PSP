import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class E2_GestionProceso {
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb = new ProcessBuilder();

        // Lanzamos un comando ping de 3 paquetes
        if (os.contains("win")) {
            pb.command("ping", "-n", "3", "127.0.0.1");
        } else {
            pb.command("ping", "-c", "3", "127.0.0.1");
        }

        try {
            Process proceso = pb.start();

            // Comprobamos si el proceso está activo en el SO
            System.out.println("¿El proceso está en ejecución?: " + proceso.isAlive());

            // Espera segura con timeout (máximo 5 segundos)
            boolean terminadoATiempo = proceso.waitFor(500, TimeUnit.SECONDS);

            if (terminadoATiempo) {
                System.out.println("El proceso finalizó a tiempo.");
                System.out.println("Código de retorno (0 = Éxito): " + proceso.exitValue());
            } else {
                System.out.println("El proceso superó el tiempo máximo. Forzando cancelación...");
                proceso.destroy(); // Forzar la terminación
            }

            System.out.println("¿El proceso sigue activo?: " + proceso.isAlive());

        } catch (IOException e) {
            System.err.println("Error al iniciar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("La espera del proceso fue interrumpida.");
            Thread.currentThread().interrupt();
        }
    }
}