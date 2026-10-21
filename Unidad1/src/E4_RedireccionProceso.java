import java.io.File;
import java.io.IOException;

public class E4_RedireccionProceso {
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        File ficheroSalida = new File("salida_proceso.txt");

        ProcessBuilder pb = new ProcessBuilder();
        if (os.contains("win")) {
            pb.command("cmd.exe", "/C", "dir");
        } else {
            pb.command("ls", "-la");
        }

        try {
            // Opción A: Redirigir la salida a un archivo en disco
            pb.redirectOutput(ficheroSalida);
            pb.redirectErrorStream(true); // Redirige también los errores al mismo archivo

            Process proceso1 = pb.start();
            proceso1.waitFor();
            System.out.println("Salida guardada en el archivo: " + ficheroSalida.getAbsolutePath());

            // Opción B: Heredar la consola de Java (los datos salen directamente por System.out)
            System.out.println("\nEjecutando con inheritIO():");
            ProcessBuilder pbConsole = new ProcessBuilder();
            if (os.contains("win")) {
                pbConsole.command("cmd.exe", "/C", "echo Hola desde el proceso hijo");
            } else {
                pbConsole.command("echo", "Hola desde el proceso hijo");
            }

            pbConsole.inheritIO(); // Muestra la salida en la consola de IntelliJ
            Process proceso2 = pbConsole.start();
            proceso2.waitFor();

        } catch (IOException e) {
            System.err.println("Error al manejar ficheros o ejecutar comando: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Interrupción durante la espera.");
            Thread.currentThread().interrupt();
        }
    }
}
