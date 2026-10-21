import java.util.concurrent.Executors;



public class P1_4_Executors {
        public static void main(String[] args) {
            try (var executor = Executors.newFixedThreadPool(2)) {
                for (int i = 1; i <= 5; i++) {
                    int id = i; // La lambda captura una variable effectively final
                    executor.submit(() -> System.out.println("Tarea " + id +
                            " en " + Thread.currentThread().getName()));

                }

            } // close() espera la finalización de las tareas enviadas
        }
    }

