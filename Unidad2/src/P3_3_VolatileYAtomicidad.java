import java.util.concurrent.atomic.AtomicInteger;

public class P3_3_VolatileYAtomicidad {
    static volatile boolean seguir = true;
    static volatile int contadorVolatile;
    static AtomicInteger contadorAtomico = new AtomicInteger();
    public static void main(String[] args) throws InterruptedException {
        Thread observador = new Thread(() -> {
            while (seguir) Thread.onSpinWait(); // SOLO demostración; desperdicia CPU
            System.out.println("[observador] Detectó seguir=false");
        });
        observador.start();
        Thread.sleep(100);
        seguir = false; // El otro hilo puede observar el cambio
        observador.join();
        int n = 4, veces = 100_000;
        Thread[] trabajadores = new Thread[n];
        for (int i = 0; i < n; i++) {
            trabajadores[i] = new Thread(() -> {
                for (int j = 0; j < veces; j++) {
                    contadorVolatile++; // NO es atómico
                    contadorAtomico.incrementAndGet(); // Sí es incremento atómico
                }
            });
            trabajadores[i].start();
        }
        for (Thread t : trabajadores) t.join();
        System.out.println("Esperado=" + n * veces);
        System.out.println("volatile int++=" + contadorVolatile);
        System.out.println("AtomicInteger=" + contadorAtomico.get());
    }
}
