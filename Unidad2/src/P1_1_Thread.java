
public class P1_1_Thread extends Thread{
    @Override public void run() {
        System.out.println("Ejecuta: " + Thread.currentThread().getName());
    }
    public static void main(String[] args) {
        P1_1_Thread h = new P1_1_Thread();
        h.start();        // Nuevo hilo
        //h.join();         // main espera a que termine
        System.out.println("Fin de main");
    }
}
