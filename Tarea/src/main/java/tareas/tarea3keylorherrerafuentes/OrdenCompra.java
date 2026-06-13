package tareas.tarea3keylorherrerafuentes;
/**
 * Tarea #4 Programación Orientada a Objetos.
 * Estudiantes: Keylor Herrera Fuentes (2021046723) Samantha Tames Piedra (2022207855)
 */
// Clase OrdenCompra


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrdenCompra  implements Serializable{
    private static int consecutivo = 1;
    private static final float IV = 0.13f;
    private final int numero;
    private final LocalDateTime fechaHora;
    private EstadoCompra estado;
    private ArrayList<LineaOrdenCompra> lineas;

    public OrdenCompra(Cliente cliente) {
        this.numero = consecutivo++;
        this.fechaHora = LocalDateTime.now();
        this.estado = EstadoCompra.INICIADA;
        this.lineas = new ArrayList<>();
        cliente.agregarOrden(this);
    }

    public void agregarLinea(float cantidad, Producto producto) {
        lineas.add(new LineaOrdenCompra(cantidad, producto));
        estado = EstadoCompra.PAGO_PENDIENTE; // Cambia el estado al agregar una línea
        producto.retirarExistencias(cantidad);
    }
    
    public ArrayList<LineaOrdenCompra> getLineas() {
        return this.lineas;
    }


    public float calcularCosto() {
        float costo = 0;
        for (LineaOrdenCompra linea : lineas) {
            costo += linea.calcularCosto();
        }
        return costo;
    }

    public float calcularImpuesto() {
        return calcularCosto() * IV; // Impuesto fijo del 13%
    }

    public float calcularTotal() {
        return calcularCosto() + calcularImpuesto();
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }
    
    public int getNumero() {
        return numero;
    }

public void borrarLinea(int indice) {
    if (indice >= 0 && indice < lineas.size()) {
        LineaOrdenCompra lineaBorrada = lineas.remove(indice);
        lineaBorrada.getProducto().agregarExistencias(lineaBorrada.getCantidad());
        System.out.println("Línea borrada de la orden de compra: " + lineaBorrada);
    } else {
        System.out.println("Índice de línea no válido.");
    }
}

public float calcularTotalPendiente() {
    float totalPendiente = 0;
    for (LineaOrdenCompra linea : lineas) {
        totalPendiente += linea.calcularCosto();
    }
    return totalPendiente;
}

    @Override
    public String toString() {
        return "OrdenCompra{" +
                "número=" + numero +
                ", fecha y hora=" + fechaHora +
                ", estado=" + estado +
                ", líneas de compra=" + lineas +
                ", costo=" + calcularCosto() +
                ", impuesto=" + calcularImpuesto() +
                ", total=" + calcularTotal() +
                '}';
    }
}