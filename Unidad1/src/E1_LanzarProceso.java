import java.io.IOException;

public class E1_LanzarProceso {
    public static void main(String[] args) {
        // 1. Detectar el Sistema Operativo para elegir el comando adecuado
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb = new ProcessBuilder();

        System.out.println("Sistema operativo detectado: " + os);

        if (os.contains("win")) {
            // En Windows ejecutamos cmd.exe con la opción /C
            pb.command("cmd.exe", "/C", "dir");
        } else {
            // En Linux / macOS ejecutamos ls -la
            pb.command("ls", "-la");
        }

        try {
            System.out.println("Lanzando el proceso...");
            // start() crea y ejecuta el proceso en el SO
            Process proceso = pb.start();
            System.out.println("Proceso iniciado con éxito.");
        } catch (IOException e) {
            System.err.println("Error al lanzar el proceso: " + e.getMessage());
        }
    }
}