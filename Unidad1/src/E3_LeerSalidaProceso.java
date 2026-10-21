import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class E3_LeerSalidaProceso {
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb = new ProcessBuilder();

        if (os.contains("win")) {
            pb.command("cmd.exe", "/C", "hostname");
        } else {
            pb.command("hostname");
        }

        try {
            Process proceso = pb.start();

            // getInputStream() lee la salida (stdout) del proceso externo
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            );

            System.out.println("--- SALIDA DEL PROCESO HIJO ---");
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("--------------------------------");

            int codigo = proceso.waitFor();
            System.out.println("Código de salida: " + codigo);

        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El hilo fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}

