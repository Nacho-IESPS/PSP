# RoboLab — Reto 1 (primera versión funcional)

**PSP · DAM 2.º · Java 21**. Esta edición usa **Swing**, incluido en el JDK, para que el proyecto funcione sin instalar JavaFX ni librerías externas. La interfaz está separada de los contratos de cada reto y puede sustituirse posteriormente sin modificar la lógica de los ejercicios.

## Qué recibe el alumnado

- Una simulación de 9 robots y 3 pasillos independientes, con menú previsto para cinco retos.
- Un vídeo MP4 grabado del comportamiento correcto (`referencia/Reto1_Pasillos.mp4`). **No** se entrega el código ni el bytecode del controlador correcto.
- Un controlador de partida **seguro, pero ineficiente** (`MiControlPasillos.java`).
- Un botón de pruebas automáticas, métricas y tres pistas graduadas.
- Un programa de pruebas autónomo (`PruebasReto1.java`).

## Abrir en IntelliJ IDEA

1. **File → Open** y selecciona esta carpeta, que incluye `pom.xml`.
2. Selecciona el **JDK 21** o posterior.
3. Ejecuta el `main()` de `es.robolab.app.Main`.
4. También puedes ejecutar el `main()` de `es.robolab.retos.reto1.PruebasReto1` sin interfaz gráfica.
5. Modifica **solo** `src/main/java/es/robolab/retos/reto1/MiControlPasillos.java`, vuelve a ejecutar las pruebas y consulta los mensajes.

También existen `scripts/ejecutar.bat` / `scripts/probar.bat` (Windows) y `.sh` (Linux), que utilizan `javac` y no requieren Maven instalado.

## Enunciado del reto 1

Tres pasillos son independientes: cada uno permite **un solo robot a la vez**. Nueve robots intentan atravesarlos (tres robots por pasillo). El alumno debe implementar `atravesar(int pasilloId, Paso paso)` para cumplir *simultáneamente* las cuatro condiciones:

1. Nunca se ejecutan dos `paso.avanzar()` a la vez en **el mismo pasillo**.
2. Debe ser posible que dos o más `paso.avanzar()` se ejecuten en paralelo en **pasillos diferentes**.
3. Cada robot completa exactamente una entrega (9 en total) y ninguno desaparece o se duplica.
4. Todos los robots terminan; no quedan hilos esperando indefinidamente.

El único código que necesita modificar el alumno es el controlador. **Se trabajará con `synchronized`**, entendido como la exclusión mutua sobre un objeto monitor. Está prohibido modificar el motor, los tests o las métricas para simular resultados correctos.

## Por qué la versión inicial falla

El método de partida usa `synchronized` en el método completo. Todos los robots se turnan sobre **el mismo monitor**, aunque utilicen pasillos independientes. El motor muestra "Simultáneos máximos: 1". Es una implementación *segura* contra colisiones, pero desaprovecha concurrencia.

**Objetivo de las pruebas:** seguridad **Y** concurrencia. No basta con que no haya colisiones.

## Qué significa cada método nuevo

- `synchronized`: ejecuta una sección crítica después de adquirir el monitor de un objeto; solo un hilo lo tiene cada vez.
- `paso.avanzar()`: operación suministrada por el motor. Simula el desplazamiento y tarda un tiempo. Puede lanzar `InterruptedException` si se interrumpe mientras espera.
- `throws InterruptedException`: permite comunicar la interrupción al código que ha llamado al método; no se necesita capturar dentro de `atravesar()`.
- `submit()`: envía una tarea al ejecutor del simulador. Esta infraestructura ya está proporcionada.
- `Future.get()`: espera el resultado de una tarea. Las pruebas lo usan con un límite de tiempo.
- `CountDownLatch`: permite que todos los robots comiencen el intento de acceso juntos, haciendo más visible la concurrencia.
- `AtomicInteger`: es un contador seguro usado **internamente por las pruebas** para medir cruces y colisiones. No deben implementarlo en este reto.

## Lectura del diagnóstico

- **Seguridad: OK**: ningún par de robots ha ocupado el mismo pasillo a la vez.
- **Pasillos independientes: OK**: ha habido al menos dos cruces simultáneos en pasillos distintos.
- **9/9 entregas: OK**: no faltan ni sobran entregas.
- **Finalización: OK**: todas las tareas han terminado dentro del límite.
- **Duración**: métrica informativa; **no** decide por sí sola si aprueba, porque depende del ordenador.

El orden exacto de los robots puede cambiar entre ejecuciones, incluso con una solución correcta. La grabación es una **referencia de comportamiento**, no una secuencia de movimientos que haya que reproducir al píxel.

## Propuesta de uso en clase (35-50 minutos)

1. Ver el vídeo y describir qué significa que los pasillos sean independientes (5 min).
2. Ejecutar la versión inicial e interpretar por qué tarda más (5 min).
3. Modificar únicamente el controlador (15-25 min).
4. Ejecutar pruebas, examinar las pistas si es necesario y justificar la solución (10 min).

## Arquitectura para los futuros retos

- `es.robolab.app`: interfaz y menú ampliable.
- `es.robolab.core`: contrato de notificaciones del motor, independiente de la interfaz.
- `es.robolab.retos.reto1`: contrato, controlador del alumno, simulación/observabilidad y resultados.
- `src/test`: lanzadores de pruebas públicas independientes del interfaz gráfico.

En versiones siguientes se agregarán retos 2–5 como paquetes independientes, reutilizando la aplicación y su panel de resultados. **Esta v1 solo implementa y evalúa el reto 1.**

## Límites conocidos de esta primera versión

- La simulación gráfica es intencionadamente sencilla (robots como círculos en carriles), no un juego con rutas ni detección física de choques.
- El test de concurrencia comprueba comportamientos observables; por sí solo no demuestra que el alumno haya utilizado obligatoriamente la sintaxis `synchronized`.
- Las pruebas son públicas y la batería es pequeña; para evaluación formal conviene añadir pruebas de estrés adicionales, sin introducir requisitos nuevos.
- La prueba comienza 9 tareas en paralelo y usa una espera breve dentro de la sección crítica para hacer observable el solapamiento. Esa espera es parte del **motor proporcionado**, no código que deba escribir el alumno.
