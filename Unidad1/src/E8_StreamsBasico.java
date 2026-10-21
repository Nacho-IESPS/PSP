import java.util.Arrays;
import java.util.List;

public class E8_StreamsBasico {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("--- 1. STREAM SECUENCIAL (.stream()) ---");
        // Procesa los elementos ordenadamente en el hilo principal (main)
        numeros.stream()
                .forEach(n -> System.out.print(n + " "));
        System.out.println("\n(Siempre mantiene el orden 1..10)");

        System.out.println("\n--- 2. STREAM PARALELO (.parallelStream()) ---");
        // Distribuye los elementos entre los hilos del ForkJoinPool (orden impredecible)
        numeros.parallelStream()
                .forEach(n -> System.out.print(n + " "));
        System.out.println("\n(El orden de salida varía en cada ejecución)");

        System.out.println("\n--- 3. PARALLEL STREAM CON forEachOrdered() ---");
        // Forzar el procesamiento/impresión respetando el orden original
        numeros.parallelStream()
                .forEachOrdered(n -> System.out.print(n + " "));
        System.out.println("\n(Forzado en orden a costa de mayor sincronización)");
    }
}