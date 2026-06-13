package tareas.tarea3keylorherrerafuentes;

import java.io.Serializable;

/**
 * Tarea #4 Programación Orientada a Objetos.
 * Estudiantes: Keylor Herrera Fuentes (2021046723) Samantha Tames Piedra (2022207855)
 */
// Clase enumerada para representar el estado de compra
public enum EstadoCompra  implements Serializable {
    INICIADA,
    PAGO_PENDIENTE,
    COMPLETADA;

    @Override
    public String toString() {
        return super.toString().replace("_", " ");
    }
}
