import java.util.Map;

public class E5_VariablesEntorno {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder();

        // pb.environment() devuelve un Map<String, String> con todas las variables del SO
        Map<String, String> entorno = pb.environment();

        System.out.println("=== VARIABLES DE ENTORNO DESTACADAS ===");

        // Consulta genérica adaptable a Windows o Linux/Mac
        String usuario = entorno.getOrDefault("USERNAME", entorno.get("USER"));
        String home = entorno.getOrDefault("USERPROFILE", entorno.get("HOME"));
        String numProcesadores = entorno.get("NUMBER_OF_PROCESSORS");
        String path = entorno.get("PATH");

        System.out.println("Usuario actual:            " + usuario);
        System.out.println("Directorio personal (Home): " + home);
        System.out.println("Procesadores (var entorno): " + numProcesadores);
        System.out.println("Ruta PATH (primeros 100 caracteres): " +
                (path != null ? path.substring(0, Math.min(100, path.length())) + "..." : "N/A"));
    }
}