import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/** Ejecutar varias veces: java P3_1_CompararContadores */
public class P3_1_CompararContadores {
    static final int HILOS = 8;
    static final int REPETICIONES = 100_000;
    static int inseguro;
    static int protegido;
    static AtomicInteger atomico;
    static LongAdder acumulador;

    static synchronized void incrementarProtegido() { protegido++; }

    static void ejecutar(String nombre, Runnable operacion, java.util.function.IntSupplier resultado)
            throws InterruptedException {
        long inicio = System.nanoTime();
        Thread[] hilos = new Thread[HILOS];
        for (int i = 0; i < HILOS; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < REPETICIONES; j++) operacion.run();
            });
            hilos[i].start();
        }
        // join() hace esperar a main hasta que CADA trabajador ha terminado.
        for (Thread hilo : hilos) hilo.join();
        long ms = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - inicio);
        System.out.printf("%-20s esperado=%d real=%d tiempo=%d ms%n",
                nombre, HILOS * REPETICIONES, resultado.getAsInt(), ms);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Misma carga, cuatro estrategias; repite para observar variaciones.");
        inseguro = 0;
        ejecutar("Sin sincronizar", () -> inseguro++, () -> inseguro);
        protegido = 0;
        ejecutar("synchronized", P3_1_CompararContadores::incrementarProtegido, () -> protegido);
        atomico = new AtomicInteger();
        ejecutar("AtomicInteger", () -> atomico.incrementAndGet(), () -> atomico.get());
        acumulador = new LongAdder();
        ejecutar("LongAdder", () -> acumulador.increment(), () -> acumulador.intValue());
        System.out.println("Nota: el caso inseguro puede acertar por casualidad. No es fiable.");
        System.out.println("Los tiempos son orientativos: no son un benchmark riguroso.");
    }
}
