import java.util.*;

public class P3_2_RecordInmutable {
    record Punto(int x, int y) {} // record genera constructor, accesores, equals, etc.
    record Equipo(List<String> integrantes) {} // OJO: la lista puede seguir siendo mutable

    public static void main(String[] args) {
        Punto p = new Punto(2, 4);
        System.out.println("Punto: " + p + "; x=" + p.x());
        // p.x = 7; // No compila: campo final
        List<String> nombres = new ArrayList<>();
        nombres.add("Ana");
        Equipo peligro = new Equipo(nombres);
        nombres.add("Luis");
        System.out.println("Record con lista compartida: " + peligro.integrantes());
        Equipo seguro = new Equipo(List.copyOf(nombres));
        nombres.add("Sara");
        System.out.println("Record con copia inmutable: " + seguro.integrantes());
        // seguro.integrantes().add("Eva"); // Lanzaría UnsupportedOperationException
    }
}
