import java.util.concurrent.*;

public class P6_1_EjecutoresComparados {
    static void demo(String nombre, ExecutorService executor) throws Exception {
        long inicio = System.nanoTime();
        System.out.println("\n=== " + nombre + " ===");
        try (executor) {  // Java 19+: espera terminación de tareas al cerrar
            Future<?>[] resultados = new Future<?>[6];
            for (int i = 0; i < 6; i++) {
                final int id = i;
                resultados[i] = executor.submit(() -> {
                    System.out.printf("tarea %d: empieza en %s%n", id, Thread.currentThread());
                    try { Thread.sleep(250); }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    System.out.println("tarea " + id + ": termina");
                });
            }
            for (Future<?> f : resultados) f.get(); // Recoge posibles fallos
        }
        System.out.printf("Tiempo aproximado: %d ms%n",
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - inicio));
    }
    public static void main(String[] args) throws Exception {
        demo("Pool fijo de 2 hilos", Executors.newFixedThreadPool(2));
        demo("Un hilo virtual por tarea", Executors.newVirtualThreadPerTaskExecutor());
        System.out.println("La mejora con virtuales es para esperas, no para cálculos de CPU.");
    }
}
