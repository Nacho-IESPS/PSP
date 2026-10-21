import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class P3_4bCASPvsSynchronized {

    private static final int NUM_HILOS = 10;
    private static final int INCREMENTOS_POR_HILO = 1_000_000; // 1 millón por hilo (10M en total)

    // Variables para la prueba CAS
    private final AtomicInteger contadorCAS = new AtomicInteger(0);
    private final AtomicInteger colisionesCAS = new AtomicInteger(0); // Cuenta los fallos de CAS

    // Variables para la prueba Synchronized
    private int contadorSync = 0;

    // 1. Incremento usando CAS (Lock-Free con medición de colisiones)
    public void incrementarCAS() {
        int valorEsperado;
        int nuevoValor;
        do {
            valorEsperado = contadorCAS.get();
            nuevoValor = valorEsperado + 1;

            // Si el CAS falla, registramos una colisión e incrementamos el contador de fallos
            if (!contadorCAS.compareAndSet(valorEsperado, nuevoValor)) {
                colisionesCAS.incrementAndGet();
                // El bucle continuará (reintento)
            } else {
                break; // Éxito, salimos del bucle
            }
        } while (true);
    }

    // 2. Incremento usando Synchronized (Bloqueo clásico)
    public synchronized void incrementarSync() {
        contadorSync++;
    }

    public static void main(String[] args) throws InterruptedException {
        P3_4bCASPvsSynchronized test = new P3_4bCASPvsSynchronized();

        System.out.println("=== INICIANDO PRUEBA DE ALTA CONTENCIÓN ===");
        System.out.println("Hilos simultáneos: " + NUM_HILOS);
        System.out.println("Incrementos totales por método: " + (NUM_HILOS * INCREMENTOS_POR_HILO) + "\n");

        // ----------------------------------------------------
        // PRUEBA SYNCHRONIZED
        // ----------------------------------------------------
        ExecutorService executorSync = Executors.newFixedThreadPool(NUM_HILOS);
        long inicioSync = System.currentTimeMillis();

        for (int i = 0; i < NUM_HILOS * INCREMENTOS_POR_HILO; i++) {
            executorSync.submit(test::incrementarSync);
        }
        executorSync.shutdown();
        executorSync.awaitTermination(1, TimeUnit.MINUTES);

        long finSync = System.currentTimeMillis();
        long tiempoSync = finSync - inicioSync;

        // ----------------------------------------------------
        // PRUEBA CAS
        // ----------------------------------------------------
        ExecutorService executorCAS = Executors.newFixedThreadPool(NUM_HILOS);
        long inicioCAS = System.currentTimeMillis();

        for (int i = 0; i < NUM_HILOS * INCREMENTOS_POR_HILO; i++) {
            executorCAS.submit(test::incrementarCAS);
        }
        executorCAS.shutdown();
        executorCAS.awaitTermination(1, TimeUnit.MINUTES);

        long finCAS = System.currentTimeMillis();
        long tiempoCAS = finCAS - inicioCAS;

        // ----------------------------------------------------
        // RESULTADOS
        // ----------------------------------------------------
        System.out.println("--- Resultados Synchronized ---");
        System.out.println("Valor final: " + test.contadorSync);
        System.out.println("Tiempo: " + tiempoSync + " ms");
        System.out.println("Colisiones detectadas: No medible (los hilos hacen cola pasivamente)\n");

        System.out.println("--- Resultados CAS (Lock-Free) ---");
        System.out.println("Valor final: " + test.contadorCAS.get());
        System.out.println("Tiempo: " + tiempoCAS + " ms");
        System.out.println("Colisiones (reintentos de bucle): " + test.colisionesCAS.get());

        double ratioEfectividad = ((double) (NUM_HILOS * INCREMENTOS_POR_HILO) / ((NUM_HILOS * INCREMENTOS_POR_HILO) + test.colisionesCAS.get())) * 100;
        System.out.printf("Eficiencia del primer intento: %.2f%%\n", ratioEfectividad);
    }
}
