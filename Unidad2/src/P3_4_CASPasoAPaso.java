import java.util.concurrent.atomic.AtomicInteger;
/** CAS = compare-and-swap: cambiar SOLO si el valor sigue siendo el esperado. */
public class P3_4_CASPasoAPaso {
    public static void main(String[] args) {
        AtomicInteger numero = new AtomicInteger(5);
        boolean primero = numero.compareAndSet(5, 6);
        System.out.println("Espero 5; cambio a 6: " + primero + "; valor=" + numero.get());
        boolean segundo = numero.compareAndSet(5, 7);
        System.out.println("Espero 5; cambio a 7: " + segundo + "; valor=" + numero.get());
        System.out.println("El segundo falla: el valor ya NO era 5.");
        System.out.println("incrementAndGet(): " + numero.incrementAndGet());
    }
}
