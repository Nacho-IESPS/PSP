public class P2_1_Interrupcion {
    public static void main(String[] args) throws InterruptedException {
        Thread trabajador = new Thread(() -> {
            long contador = 0;
            while (!Thread.currentThread().isInterrupted()) {
                contador++; // Simulación de trabajo CPU
            }
            System.out.println("Cancelado; iteraciones: " + contador);
        });
        trabajador.start();
        Thread.sleep(100);       // main deja trabajar al otro hilo
        trabajador.interrupt();  // main solicita la cancelación
        trabajador.join();       // main espera a su terminación
    }
}

