import java.util.concurrent.*;

public class P6_2_FutureYExcepciones {
    public static void main(String[] args) throws InterruptedException {
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            Callable<Integer> bien = () -> { Thread.sleep(200); return 21 * 2; };
            Callable<Integer> mal = () -> { throw new java.io.IOException("Archivo inexistente (simulado)"); };
            Future<Integer> ok = executor.submit(bien);
            Future<Integer> fallo = executor.submit(mal);
            try {
                System.out.println("Resultado correcto: " + ok.get());
                System.out.println("Resultado fallido: " + fallo.get());
            } catch (ExecutionException e) {
                System.out.println("La tarea lanzó: " + e.getCause());
            }
        }
    }
}
