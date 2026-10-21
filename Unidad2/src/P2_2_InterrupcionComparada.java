public class P2_2_InterrupcionComparada {
    public static void main(String[] args) throws InterruptedException {
        Thread trabajador = new Thread(() -> {
            try {
                System.out.println("[trabajador] Duermo 10 s...");
                Thread.sleep(10_000);
                System.out.println("[trabajador] Desperté normalmente");
            } catch (InterruptedException e) {
                System.out.println("[trabajador] InterruptedException. Flag en catch: "
                        + Thread.currentThread().isInterrupted()); // false: sleep limpió el flag
                Thread.currentThread().interrupt(); // Mantener la señal para capas superiores
                System.out.println("[trabajador] Flag restaurado: "
                        + Thread.currentThread().isInterrupted());
            } finally {
                System.out.println("[trabajador] Limpieza y fin");
            }
        }, "trabajador");
        trabajador.start();
        Thread.sleep(300);  // main espera antes de enviar la señal
        System.out.println("[main] Llamo a trabajador.interrupt()");
        trabajador.interrupt();
        trabajador.join();  // main espera hasta que el trabajador ha finalizado
        System.out.println("[main] Fin; trabajador.isAlive()=" + trabajador.isAlive());
    }
}
