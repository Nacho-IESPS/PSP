import java.util.ArrayList;
import java.util.List;

public class Ejemplo9_ComparativaRendimiento {

    public static void main(String[] args) {
        int N = 100_000; // Número de elementos
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            datos.add(i);
        }

        System.out.println("=== COMPARATIVA DE RENDIMIENTO Y FÓRMULA NQ ===");
        System.out.println("Elementos (N): " + N);
        System.out.println("Núcleos de CPU disponibles: " + Runtime.getRuntime().availableProcessors());
        System.out.println("-------------------------------------------------");

        // 1. Ejecución Secuencial
        long inicio = System.nanoTime();
        long sumaSecuencial = datos.stream()
                .map(i -> (int) Math.sqrt(i))
                .map(Ejemplo9_ComparativaRendimiento::operacionCostosa)
                .reduce(0, Integer::sum);
        double tiempoSecuencialMs = (System.nanoTime() - inicio) / 1_000_000.0;

        // 2. Ejecución Paralela
        inicio = System.nanoTime();
        long sumaParalela = datos.parallelStream()
                .map(i -> (int) Math.sqrt(i))
                .map(Ejemplo9_ComparativaRendimiento::operacionCostosa)
                .reduce(0, Integer::sum);
        double tiempoParaleloMs = (System.nanoTime() - inicio) / 1_000_000.0;

        // 3. Verificación de integridad de resultados
        System.out.println("Resultado Secuencial: " + sumaSecuencial);
        System.out.println("Resultado Paralelo:   " + sumaParalela);
        System.out.println("¿Resultados idénticos?: " + (sumaSecuencial == sumaParalela ? "SÍ ✓" : "NO ✗"));
        System.out.println("-------------------------------------------------");

        // 4. Métricas de tiempo y aceleración
        System.out.printf("Tiempo Secuencial: %.2f ms\n", tiempoSecuencialMs);
        System.out.printf("Tiempo Paralelo:   %.2f ms\n", tiempoParaleloMs);
        if (tiempoParaleloMs > 0) {
            double mejora = tiempoSecuencialMs / tiempoParaleloMs;
            System.out.printf("Aceleración obtenida: x%.2f\n", mejora);
        }

        // 5. Evaluación de la Fórmula NQ de Brian Goetz
        System.out.println("-------------------------------------------------");
        long Q = 1000; // Trabajo aproximado por elemento en operacionCostosa
        long NQ = (long) N * Q;
        System.out.println("Cálculo N × Q: " + N + " × " + Q + " = " + NQ);
        System.out.println("¿Compensa paralelizar? (N × Q > 10.000): " +
                (NQ > 10000 ? "SÍ (El beneficio supera el overhead de hilos)" : "NO"));
    }

    // Método que simula una carga de trabajo computacional por cada elemento (Q)
    public static int operacionCostosa(int numero) {
        int suma = 0;
        for (int i = 1; i < 1000; i++) {
            suma += numero / i;
        }
        return suma;
    }
}