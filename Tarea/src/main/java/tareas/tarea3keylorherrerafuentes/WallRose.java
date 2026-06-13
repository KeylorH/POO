package tareas.tarea3keylorherrerafuentes;
/**
 * Tarea #4 Programación Orientada a Objetos.
 * Estudiantes: Keylor Herrera Fuentes (2021046723) Samantha Tames Piedra (2022207855)
 */
// Clase controladora WallRose
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class WallRose  implements Serializable{
    private static final long serialVersionUID = 819225927033623616L;
    private static final String FILENAME = "tiendawallrose_data.ser";    
    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Producto> productos = new ArrayList<>();
    public final ArrayList<OrdenCompra> ordenesCompra = new ArrayList<>();
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<String> verListaClientes() {
        ArrayList<String> listaClientes = new ArrayList<>();
        for (Cliente cliente : clientes) {
            listaClientes.add(cliente.toString());
        }
        return listaClientes;
    }

    public int agregarCliente(String nombre, String email) {
    Cliente cliente = new Cliente(nombre, email);
    clientes.add(cliente);
    return clientes.size(); // Devuelve el tamaño del ArrayList, que es el número secuencial del cliente
}

    public ArrayList<String> obtenerOrdenesClienteTodas(int numeroCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getNumero() == numeroCliente) {
                ArrayList<String> listaOrdenes = new ArrayList<>();
                for (OrdenCompra orden : cliente.obtenerOrdenesTodas()) {
                    listaOrdenes.add(orden.toString());
                }
                return listaOrdenes;
            }
        }
        return null; // Cliente no encontrado
    }

    public ArrayList<String> obtenerOrdenesClienteIniciadas(int numeroCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.hashCode() == numeroCliente) {
                ArrayList<String> listaOrdenes = new ArrayList<>();
                for (OrdenCompra orden : cliente.obtenerOrdenesIniciadas()) {
                    listaOrdenes.add(orden.toString());
                }
                return listaOrdenes;
            }
        }
        return null; // Cliente no encontrado
    }

    public ArrayList<String> obtenerOrdenesClientePendientes(int numeroCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.hashCode() == numeroCliente) {
                ArrayList<String> listaOrdenes = new ArrayList<>();
                for (OrdenCompra orden : cliente.obtenerOrdenesPendientes()) {
                    listaOrdenes.add(orden.toString());
                }
                return listaOrdenes;
            }
        }
        return null; // Cliente no encontrado
    }

    public ArrayList<String> obtenerOrdenesClienteCompletadas(int numeroCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.hashCode() == numeroCliente) {
                ArrayList<String> listaOrdenes = new ArrayList<>();
                for (OrdenCompra orden : cliente.obtenerOrdenesCompletadas()) {
                    listaOrdenes.add(orden.toString());
                }
                return listaOrdenes;
            }
        }
        return null; // Cliente no encontrado
    }
    
    // Método para calcular el total pendiente de un cliente
    public float calcularTotalPendienteCliente(int numeroCliente) {
        float totalPendienteCliente = 0;

        // Busca el cliente con el número especificado
        for (Cliente cliente : clientes) {
            if (cliente.getNumero() == numeroCliente) {
                // Obtiene todas las órdenes de compra del cliente
                ArrayList<OrdenCompra> ordenesCliente = cliente.obtenerOrdenesTodas();
                // Suma el total pendiente de cada orden de compra
                for (OrdenCompra orden : ordenesCliente) {
                    totalPendienteCliente += orden.calcularTotalPendiente();
                }
                // Una vez que se ha calculado el total pendiente, se sale del ciclo
                break;
            }
        }

        return totalPendienteCliente;
    }

public void crearOrden(int numeroCliente) {
    Cliente cliente = null;
    for (Cliente c : clientes) {
        if (c.getNumero() == numeroCliente) {
            cliente = c;
            break;
        }
    }

    if (cliente != null) {
        OrdenCompra orden = new OrdenCompra(cliente);
        ordenesCompra.add(orden); 
        System.out.println("Orden de compra creada del cliente #" + numeroCliente + ". Número de orden: " + orden.getNumero());
    } else {
        System.out.println("No se encontró ningún registrado cliente con ese número: " + numeroCliente);
    }
}



void borrarLineaOrdenCompra(int numeroOrden, int indiceLinea) {
    for (OrdenCompra orden : ordenesCompra) {
        if (orden.getNumero() == numeroOrden) {
            ArrayList<LineaOrdenCompra> lineas = orden.getLineas();
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).getIndice() == indiceLinea) {
                    lineas.remove(i);
                    System.out.println("Línea de orden de compra eliminada exitosamente.");
                    return;
                }
            }
            System.out.println("No se encontró la línea de orden de compra especificada.");
            return;
        }
    }
    System.out.println("No se encontró la orden de compra especificada.");
}


    float calcularTotalPendiente(int numeroOrden) {
		for (OrdenCompra orden : ordenesCompra) {
			if (orden.getNumero() == numeroOrden) {
				return orden.calcularTotalPendiente();
			}
		}
		System.out.println("No se encontró la orden de compra especificada.");
		return 0; 
	}

void agregarLineaOrdenCompra(int numeroOrden, float cantidad, Producto producto) {
    boolean ordenEncontrada = false;
    for (OrdenCompra orden : ordenesCompra) {
        if (orden.getNumero() == numeroOrden) {
            orden.agregarLinea(cantidad, producto);
            ordenEncontrada = true;
            System.out.println("Línea de orden de compra agregada exitosamente.");
            break;
        }
    }
    
    if (!ordenEncontrada) {
        System.out.println("No se encontró la orden de compra especificada.");
    }
}




    public ArrayList<String> verListaOrdenesCompra() {
        ArrayList<String> listaOrdenes = new ArrayList<>();
        for (OrdenCompra orden : ordenesCompra) {
            listaOrdenes.add(orden.toString());
        }
        return listaOrdenes;
    }



    // Método para guardar los datos en el archivo
    public static WallRose cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (WallRose) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
            return new WallRose();
        }
    }

    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(this);
            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guard"
                    + "ar los datos: " + e.getMessage());
        }
    }

    public void borrarDatosGuardados() {
        File file = new File(FILENAME);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Datos guardados borrados exitosamente.");
                System.exit(0);
            } else {
                System.out.println("Error al intentar borrar los datos guardados.");
            }
        } else {
            System.out.println("No hay datos guardados para borrar.");
        }
    }

}
