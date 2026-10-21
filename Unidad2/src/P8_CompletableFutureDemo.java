import java.util.concurrent.*;

public class P8_CompletableFutureDemo {
    public static void main(String[] args) {
        CompletableFuture<Void> fin = CompletableFuture
                .supplyAsync(() -> "datos")
                .thenApply(texto -> texto.toUpperCase())
                .thenAccept(texto -> System.out.println("Resultado: " + texto))
                .exceptionally(error -> {
                    System.out.println("Falló la cadena: " + error);
                    return null;
                });
        fin.join(); // Espera la terminación; en CompletableFuture.join() no se declara InterruptedException
        System.out.println("Cadena terminada");
    }
}
