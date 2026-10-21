public class E7_RuntimeInformacion {
    public static void main(String[] args) {
        // Obtenemos la instancia Singleton de Runtime
        Runtime r = Runtime.getRuntime();

        // 1. Número de núcleos lógicos disponibles para la JVM
        int procesadores = r.availableProcessors();

        // 2. Consulta de la memoria de la JVM (en bytes)
        long maxBytes = r.maxMemory();     // Límite máximo (-Xmx)
        long totalBytes = r.totalMemory(); // Memoria asignada actualmente por el SO
        long libreBytes = r.freeMemory();  // Memoria libre dentro de la asignada
        long usadaBytes = totalBytes - libreBytes; // Memoria en uso real

        // Conversión a Mebibytes (MiB = bytes / 1024 / 1024)
        long maxMiB = maxBytes / 1024 / 1024;
        long totalMiB = totalBytes / 1024 / 1024;
        long libreMiB = libreBytes / 1024 / 1024;
        long usadaMiB = usadaBytes / 1024 / 1024;

        System.out.println("=== INFORMACIÓN DEL ENTORNO DE EJECUCIÓN (JVM) ===");
        System.out.println("Núcleos de CPU disponibles: " + procesadores);
        System.out.println("-------------------------------------------------");
        System.out.println("Memoria Máxima JVM (-Xmx):  " + maxMiB + " MiB");
        System.out.println("Memoria Total Asignada:     " + totalMiB + " MiB");
        System.out.println("Memoria Libre Asignada:     " + libreMiB + " MiB");
        System.out.println("Memoria Real en Uso:        " + usadaMiB + " MiB");
        System.out.printf("Porcentaje de uso actual:    %.2f%%\n", ((double) usadaBytes / totalBytes) * 100);
    }
}