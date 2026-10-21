public class P1_3_lambdas {
    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(new Runnable() {
            @Override public void run() { System.out.println("Anónima"); }
        });
        Thread b = new Thread(() -> System.out.println("Lambda"));
        var c = new Thread(() -> System.out.println("var + lambda"));
        // c tiene tipo Thread, inferido por el compilador.
        a.start(); b.start(); c.start();
        a.join(); b.join(); c.join();
    }
}

