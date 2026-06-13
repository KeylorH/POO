package tareas.tarea3keylorherrerafuentes;
/**
 * Tarea #4 Programación Orientada a Objetos.
 * Estudiantes: Keylor Herrera Fuentes (2021046723) Samantha Tames Piedra (2022207855)
 */
// Clase Cliente


import java.io.Serializable;
import java.util.ArrayList;

public class Cliente  implements Serializable{
private static final long serialVersionUID = -3470580522489955L;

    private static int consecutivo = 1;
    private final int numero;
    private final String nombre;
    private final String email;
    private final ArrayList<OrdenCompra> ordenes;

    public Cliente(String nombre, String email) {
        this.numero = consecutivo++;
        this.nombre = nombre;
        this.email = email;
        this.ordenes = new ArrayList<>();
    }
    
        public String getNombre() {
        return nombre;
    }
        
    public int getNumero() {
        return numero;
    }

    public void agregarOrden(OrdenCompra orden) {
        ordenes.add(orden);
    }

    public ArrayList<OrdenCompra> obtenerOrdenesTodas() {
        return ordenes;
    }

    public ArrayList<OrdenCompra> obtenerOrdenesFiltradas(EstadoCompra estado) {
        ArrayList<OrdenCompra> filtradas = new ArrayList<>();
        for (OrdenCompra orden : ordenes) {
            if (orden.getEstado() == estado) {
                filtradas.add(orden);
            }
        }
        return filtradas;
    }


    public ArrayList<OrdenCompra> obtenerOrdenesPendientes() {
        return obtenerOrdenesFiltradas(EstadoCompra.PAGO_PENDIENTE);
    }

    public ArrayList<OrdenCompra> obtenerOrdenesIniciadas() {
        return obtenerOrdenesFiltradas(EstadoCompra.INICIADA);
    }

    public ArrayList<OrdenCompra> obtenerOrdenesCompletadas() {
        return obtenerOrdenesFiltradas(EstadoCompra.COMPLETADA);
    }


    @Override
    public String toString() {
        return "Cliente " +
                "número=" + numero +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' ;
    }

}

