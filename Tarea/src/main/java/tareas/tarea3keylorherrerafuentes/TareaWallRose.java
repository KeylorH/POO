package tareas.tarea3keylorherrerafuentes;
/**
 * Tarea #4 Programación Orientada a Objetos.
 * Estudiantes: Keylor Herrera Fuentes (2021046723) Samantha Tames Piedra (2022207855)
 */
// Clase principal para manejar la interacción con el usuario
import java.util.ArrayList;
import java.util.Scanner;

public class TareaWallRose {
    private static WallRose wallRose;    
    
    public static void main(String[] args) {
        wallRose = WallRose.cargarDatos();
        if (wallRose == null) {
            wallRose = new WallRose();
        }
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;
            do {
                menuPrincipal();
                opcion = scanner.nextInt();
                scanner.nextLine(); 
                
                switch (opcion) {
                    case 1 -> menuClientes(wallRose, scanner);
                    case 2 -> menuOrdenes(wallRose, scanner);
                    case 3 -> menuProductos(wallRose, scanner);
                    case 4 -> wallRose.borrarDatosGuardados();
                    case 5 -> {
                        wallRose.guardarDatos(); 
                        System.out.println("¡Gracias por preferirnos!");
                        System.exit(0);
                    }
                    default -> System.out.println("Opción no válida. Por favor, ingrese una opción del menú.");
                }
            } while (opcion != 0);
            //guardarDatos(wallRose);
        }
    }
    
    private static void menuPrincipal() {
        System.out.println("""
                           -----------------------------
                           °                           °
                           °  ¡Bienvenidos a WallRose! °
                           °                           °
                           -----------------------------""");
        System.out.println("\n*** MENÚ PRINCIPAL ***");
        System.out.println("1. Clientes");
        System.out.println("2. Órdenes de Compra");
        System.out.println("3. Productos");
        System.out.println("4. Borrar datos guardados");
        System.out.println("5. Salir");
        System.out.print("Ingrese una opción: ");
    }
    
    private static void menuClientes(WallRose wallRose, Scanner scanner) {
        int opcion;
        do {
            mostrarMenuClientes();
            opcion = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (opcion) {
            case 1 -> imprimirListaClientes(wallRose);
            case 2 -> agregarCliente(wallRose, scanner);
            case 3 -> imprimirListaOrdenes(wallRose, scanner);
            case 4 -> mostrarOrdenesIniciadas(wallRose, scanner);
            case 5 -> moostrarOrdenesPendientes(wallRose, scanner);
            case 6 -> mostrarOrdenesCompletadas(wallRose, scanner);
            case 7 -> calcularTotalPendienteCliente(wallRose, scanner);
                case 8 -> {
                    return;
                }
                default -> System.out.println("Opción no válida. Por favor, ingrese una opción del menú.");
            }
        } while (opcion != 0);
    }
    
    private static void mostrarMenuClientes() {
        System.out.println("\n*** MENÚ CLIENTES ***");
        System.out.println("1. Ver lista de clientes");
        System.out.println("2. Agregar cliente");
        System.out.println("3. Ver todas las órdenes de un cliente");
        System.out.println("4. Ver órdenes iniciadas de un cliente");
        System.out.println("5. Ver órdenes pendientes de pago de un cliente");
        System.out.println("6. Ver órdenes completas de un cliente");
        System.out.println("7. Calcular total pendiente de un cliente");
        System.out.println("8. Volver al menú principal");
        System.out.print("Ingrese una opción: ");
    }
    
    private static void menuOrdenes(WallRose wallRose, Scanner scanner) {
        int opcion;
        do {
            mostrarMenuOrdenes();
            opcion = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (opcion) {
            case 1 -> mostrarListaOrdenesCompra(wallRose);
            case 2 -> crearNuevaOrdenCompra(wallRose, scanner);
            case 3 -> agregarLineaOrdenCompra(wallRose, scanner);
            case 4 -> borrarLineaOrdenCompra(wallRose, scanner);
            case 5 -> calcularTotalPendiente(wallRose, scanner);
                case 6 -> {
                    return;
                }
                default -> System.out.println("Opción no válida. Por favor, ingrese una opción del menú.");
            }
        } while (opcion != 0);
    }
    
    private static void mostrarMenuOrdenes() {
        System.out.println("\n*** MENÚ ÓRDENES DE COMPRA ***");
        System.out.println("1. Ver lista de órdenes de compra");
        System.out.println("2. Crear nueva orden de compra");
        System.out.println("3. Agregar línea a una orden de compra");
        System.out.println("4. Borrar línea de una orden de compra");
        System.out.println("5. Calcular total pendiente de una orden de compra");
        System.out.println("6. Volver al menú principal");
        System.out.print("Ingrese una opción: ");
    }
    
    private static void menuProductos(WallRose wallRose, Scanner scanner) {
        int opcion;
        do {
            mostrarMenuProductos();
            opcion = scanner.nextInt();
            scanner.nextLine();
            
        switch (opcion) {
            case 1 -> imprimirListaProductos(wallRose);
            case 2 -> agregarProducto(wallRose, scanner);
            case 3 -> agregarExistenciasProducto(wallRose, scanner);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Opción no válida. Por favor, ingrese una opción del menú.");
            }
        } while (opcion != 0);
    }
    
    private static void mostrarMenuProductos() {
        System.out.println("\n*** MENÚ PRODUCTOS ***");
        System.out.println("1. Ver lista de productos");
        System.out.println("2. Agregar producto");
        System.out.println("3. Agregar existencias a un producto");
        System.out.println("4. Volver al menú principal");
        System.out.print("Ingrese una opción: ");
    }
    
    private static void imprimirListaClientes(WallRose wallRose) {
        ArrayList<String> listaClientes = wallRose.verListaClientes();
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados en WallRose.");
        } else {
            System.out.println("\n*** LISTA DE CLIENTES REGISTRADOS ***");
            for (String cliente : listaClientes) {
                System.out.println(cliente);
            }
        }
    }
   
    
    private static void imprimirListaOrdenes(WallRose wallRose, Scanner scanner) {
    imprimirListaClientes(wallRose);
    System.out.print("Ingrese el número del cliente: ");
    int numeroCliente = scanner.nextInt();
    scanner.nextLine(); 
    ArrayList<String> ordenesCliente = wallRose.obtenerOrdenesClienteTodas(numeroCliente);
    if (ordenesCliente != null && !ordenesCliente.isEmpty()) {
        System.out.println("\n*** ÓRDENES DE COMPRA DEL CLIENTE ***");
        for (String orden : ordenesCliente) {
            System.out.println(orden);
        }
    } else {
        System.out.println("No se encontraron órdenes de compra para el cliente con el número: " + numeroCliente);
    
    }
    }

 private static void imprimirListaProductos(WallRose wallRose) {
    System.out.println("\n*** LISTA DE PRODUCTOS ***");
    for (Producto producto : wallRose.getProductos()) {
        System.out.println(producto);
    }
}
//clientes 
private static void agregarCliente(WallRose wallRose, Scanner scanner) {
    System.out.print("Ingrese nombre del cliente: ");
    String nombre = scanner.nextLine();
    System.out.print("Ingrese el email del cliente: ");
    String email = scanner.nextLine();
    int numeroCliente = wallRose.agregarCliente(nombre, email); 
    System.out.println("Nuevo ingreso. Número de cliente: " + numeroCliente);
}
private static void mostrarOrdenesIniciadas(WallRose wallRose, Scanner scanner) {
    imprimirListaOrdenes(wallRose, scanner);
    System.out.print("Ingrese el número del cliente: ");
    int numeroCliente = scanner.nextInt();
    scanner.nextLine(); 
    
    ArrayList<String> ordenesIniciadasCliente = wallRose.obtenerOrdenesClienteIniciadas(numeroCliente);
    if (ordenesIniciadasCliente != null && !ordenesIniciadasCliente.isEmpty()) {
        System.out.println("\n*** ÓRDENES INICIADAS DEL CLIENTE ***");
        for (String orden : ordenesIniciadasCliente) {
            System.out.println(orden);
        }
    } else {
        System.out.println("No se encontraron órdenes iniciadas para el cliente con el número: " + numeroCliente);
    }
}

private static void moostrarOrdenesPendientes(WallRose wallRose, Scanner scanner) {
    imprimirListaOrdenes(wallRose, scanner);
    System.out.print("Ingrese el número del cliente: ");
    int numeroCliente = scanner.nextInt();
    scanner.nextLine(); 
    
    ArrayList<String> ordenesPendientesCliente = wallRose.obtenerOrdenesClientePendientes(numeroCliente);
    if (ordenesPendientesCliente != null && !ordenesPendientesCliente.isEmpty()) {
        System.out.println("\n***ÓRDENES PENDIENTES DEL CLIENTE***");
        for (String orden : ordenesPendientesCliente) {
            System.out.println(orden);
        }
    } else {
        System.out.println("No se encontraron órdenes pendientes para el cliente con el número: " + numeroCliente);
    }
}

private static void mostrarOrdenesCompletadas(WallRose wallRose, Scanner scanner) {
    imprimirListaOrdenes(wallRose, scanner);
    System.out.print("Ingrese el número del cliente: ");
    int numeroCliente = scanner.nextInt();
    scanner.nextLine(); 
    
    ArrayList<String> ordenesCompletadasCliente = wallRose.obtenerOrdenesClienteCompletadas(numeroCliente);
    if (ordenesCompletadasCliente != null && !ordenesCompletadasCliente.isEmpty()) {
        System.out.println("\n***ÓRDENES COMPLETADAS DEL CLIENTE ***");
        for (String orden : ordenesCompletadasCliente) {
            System.out.println(orden);
        }
    } else {
        System.out.println("No se encontraron órdenes completadas para el cliente con el número: " + numeroCliente);
    }
}

private static void calcularTotalPendienteCliente(WallRose wallRose, Scanner scanner) {
    imprimirListaOrdenes(wallRose, scanner);
    System.out.print("Ingrese el número del cliente: ");
    int numeroCliente = scanner.nextInt();
    scanner.nextLine(); 
    
    float totalPendienteCliente = wallRose.calcularTotalPendienteCliente(numeroCliente);
    System.out.println("El total pendiente para el cliente #" + numeroCliente + " es: $" + totalPendienteCliente);
}


//productos
private static void agregarProducto(WallRose wallRose, Scanner scanner) {
    System.out.print("Nombre del producto: ");
    String nombre = scanner.nextLine();
    System.out.print("Precio del producto: ");
    float precio = scanner.nextFloat();
    scanner.nextLine(); 
    System.out.print("Unidad de medida del producto: ");
    String unidad = scanner.nextLine();
    System.out.print("Existencias del producto: ");
    float existencias = scanner.nextFloat();
    scanner.nextLine(); 
    
    Producto producto = new Producto(nombre, precio, unidad, existencias);
    wallRose.getProductos().add(producto);
    System.out.println("Producto agregado: " + producto);
}

private static void agregarExistenciasProducto(WallRose wallRose, Scanner scanner) {
    imprimirListaProductos(wallRose);
    System.out.print("Ingrese el código del producto: ");
    int codigoProducto = scanner.nextInt();
    System.out.print("Ingrese la cantidad de existencias a agregar: ");
    float cantidad = scanner.nextFloat();
    scanner.nextLine(); 
    
    for (Producto producto : wallRose.getProductos()) {
        if (producto.getCodigo() == codigoProducto) {
            producto.agregarExistencias(cantidad);
            System.out.println("Información del producto actualizado: " + producto);
            return;
        }
    }
    System.out.println("No se encontró ningún producto con el código: " + codigoProducto);
}

//Ordenes de compra
private static void mostrarListaOrdenesCompra(WallRose wallRose) {
    ArrayList<String> listaOrdenes = wallRose.verListaOrdenesCompra();
    if (listaOrdenes != null && !listaOrdenes.isEmpty()) {
        System.out.println("*** LISTA DE ÓRDENES DE COMPRA ***");
        for (String orden : listaOrdenes) {
            System.out.println(orden);
        }
    } else {
        System.out.println("No hay órdenes de compra registradas.");
    }
}


private static void crearNuevaOrdenCompra(WallRose wallRose, Scanner scanner) {
    imprimirListaClientes(wallRose);
    System.out.print("Ingrese el número del cliente para crear la orden de compra: ");
    int numeroCliente = scanner.nextInt();
    scanner.nextLine(); 
    wallRose.crearOrden(numeroCliente);
}

private static void agregarLineaOrdenCompra(WallRose wallRose, Scanner scanner) {
    imprimirListaOrdenes(wallRose, scanner);
    System.out.print("Ingrese el número de orden de compra: ");
    int numeroOrden = scanner.nextInt();
    scanner.nextLine(); 

    System.out.print("Ingrese la cantidad a agregar: ");
    float cantidad = scanner.nextFloat();
    scanner.nextLine(); 

    imprimirListaProductos(wallRose);
    System.out.print("Ingrese el código del producto: ");
    int codigoProducto = scanner.nextInt();
    scanner.nextLine(); 

    Producto producto = null;
    for (Producto p : wallRose.getProductos()) {
        if (p.getCodigo() == codigoProducto) {
            producto = p;
            break;
        }
    }

    if (producto != null) {
        try {
            wallRose.agregarLineaOrdenCompra(numeroOrden, cantidad, producto);
            System.out.println("Línea de compra procesada correctamente a la orden");
        } catch (Exception e) {
            System.out.println("Error al agregar línea a la orden: " + e.getMessage());
        }
    } else {
        System.out.println("No se encontró ningún producto con el código: " + codigoProducto);
    }
}


private static void borrarLineaOrdenCompra(WallRose wallRose, Scanner scanner) {
    imprimirListaOrdenes(wallRose,scanner);
    System.out.print("Ingrese el número de orden de compra: ");
    int numeroOrden = scanner.nextInt();
    scanner.nextLine(); 
    System.out.print("Ingrese el código de la línea a eliminar: ");
    int indiceLinea = scanner.nextInt();
    scanner.nextLine(); 

    wallRose.borrarLineaOrdenCompra(numeroOrden, indiceLinea);
}

private static void calcularTotalPendiente(WallRose wallRose, Scanner scanner) {
    System.out.print("Ingrese el número de orden de compra: ");
    int numeroOrden = scanner.nextInt();
    scanner.nextLine(); 

    float totalPendiente = wallRose.calcularTotalPendiente(numeroOrden);
    System.out.println("El total pendiente para la orden de compra #" + numeroOrden + " es: $" + totalPendiente);
}
}

