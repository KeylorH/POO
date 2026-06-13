package tareas.tarea3keylorherrerafuentes;

import java.io.Serializable;

/**
 * Tarea #4 Programación Orientada a Objetos.
 * Estudiantes: Keylor Herrera Fuentes (2021046723) Samantha Tames Piedra (2022207855)
 */
// Clase para representar las líneas de una orden de compra


public class LineaOrdenCompra  implements Serializable{
    private final float cantidad;
    private final Producto producto;
    private static int consecutivo = 1;
    private final int indice;

    public LineaOrdenCompra(float cantidad, Producto producto) {
        this.indice = consecutivo++;
        this.cantidad = cantidad;
        this.producto = producto;
    }
    
    public float getCantidad() {
        return cantidad;
    }

    public Producto getProducto() {
        return producto;
    }
    
    public int getIndice() {
        return indice;
    }
    
    public float calcularCosto() {
        return cantidad * producto.precio;
    }
    
    @Override
    public String toString() {
        return "\n -LineaOrdenCompra{ número= " + indice +
                ", cantidad=" + cantidad +
                ", producto=" + producto +
                '}';
    }
}