import java.util.concurrent.*;

public class P5_2_ProductorConsumidorDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> cola = new ArrayBlockingQueue<>(2);
        int FIN = -1; // Mensaje especial que indica fin de producción
        Thread productor = new Thread(() -> {
            try {
                for (int n = 1; n <= 5; n++) {
                    cola.put(n); // Si está llena, espera sin consultar en bucle
                    System.out.println("[productor] coloca " + n);
                }
                cola.put(FIN);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        Thread consumidor = new Thread(() -> {
            try {
                while (true) {
                    int n = cola.take(); // Si está vacía, espera
                    if (n == FIN) break;
                    System.out.println("[consumidor] procesa " + n);
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        productor.start(); consumidor.start();
        productor.join(); consumidor.join();
        System.out.println("Ambos hilos terminaron");
    }
}
