/** Demuestra por qué no conviene tragarse InterruptedException. Java 21+. */
public class P2_2_InterrupcionIgnoradaVsConservada {
    static Thread crearTrabajador(String nombre, boolean conserva) {
        return new Thread(() -> {
            for (int paso = 1; paso <= 3; paso++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("[" + nombre + "] he observado la señal: termino");
                    return;
                }
                try {
                    System.out.println("[" + nombre + "] trabajo " + paso);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("[" + nombre + "] sleep lanzó InterruptedException; flag="
                            + Thread.currentThread().isInterrupted());
                    if (conserva) {
                        Thread.currentThread().interrupt();
                        System.out.println("[" + nombre + "] restauro el flag; terminaré en el siguiente control");
                    } else {
                        System.out.println("[" + nombre + "] ignoro la señal y continúo (ERROR de diseño)");
                    }
                }
            }
            System.out.println("[" + nombre + "] he completado todos los pasos");
        }, nombre);
    }
    public static void main(String[] args) throws InterruptedException {
        Thread ignorada = crearTrabajador("IGNORA", false);
        Thread conservada = crearTrabajador("CONSERVA", true);
        ignorada.start(); conservada.start();
        Thread.sleep(90);
        System.out.println("[main] Solicito interrupción a AMBOS trabajadores");
        ignorada.interrupt(); conservada.interrupt();
        ignorada.join(); conservada.join();
        System.out.println("[main] Ambos han terminado; compara los pasos ejecutados");
    }
}
