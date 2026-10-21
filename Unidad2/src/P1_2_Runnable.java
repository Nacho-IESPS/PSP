public class P1_2_Runnable implements Runnable {
    @Override public void run() {
        System.out.println("Tarea en " + Thread.currentThread().getName());
    }
    public static void main(String[] args) throws InterruptedException {
        Runnable tarea = new P1_2_Runnable();
        Thread hilo = new Thread(tarea);
        hilo.start();
        hilo.join();
    }
}
