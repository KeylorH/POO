package tareas.tarea1;

import java.util.ArrayList;

public class Aplicacion {
    public static void main(String[] args) {
        // 1. Declarar una coleccion de cuentas
        ArrayList<Cuenta> cuentas = new ArrayList<>();

        // 2. Crear y agregar diferentes instancias a la coleccion de cuentas
        cuentas.add(new Cuenta("Juan Perez", 10000.0));
        cuentas.add(new Cuenta("Ana Lopez", 5000.0));
        cuentas.add(new Cuenta("Carlos Sanchez", 15000.0));
        cuentas.add(new Cuenta("Maria Rodriguez", 2000.0));
        cuentas.add(new Cuenta("Luis Gomez", 8000.0));
        cuentas.add(new Cuenta("Laura Martinez", 3000.0));
        cuentas.add(new Cuenta("Pedro Ramirez", 12000.0));
        cuentas.add(new Cuenta("Sofia Herrera", 6000.0));

        // 3. Recorrer la coleccion e imprimir el resultado del metodo toString de cada cuenta
        for (Cuenta cuenta : cuentas) {
            System.out.println(cuenta.toString());
        }

        // 4. Utilizar metodos set para alterar el nombre del cliente y el saldo
        cuentas.get(0).setCliente("Jose Pablo Arend");
        cuentas.get(0).setSaldo(15000.0);
        
        cuentas.get(2).setCliente("Susan Fuentes");
        cuentas.get(2).setSaldo(20000.0);
        
        cuentas.get(3).setCliente("Estephanie Arias");
        cuentas.get(3).setSaldo(70000.0);
        
        cuentas.get(7).setCliente("Javier Arias");
        cuentas.get(7).setSaldo(3000.0);

        // Imprimir cambio
        System.out.println("\nCambio en la primera cuenta:");
        System.out.println("Cliente anterior: Juan Perez, Nuevo cliente: " + cuentas.get(0).getCliente());
        System.out.println("Saldo anterior: 10000.0, Nuevo saldo: " + cuentas.get(0).getSaldo());
        
        System.out.println("\nCambio en la tercera cuenta:");
        System.out.println("Cliente anterior: Carlos Sanchez, Nuevo cliente: " + cuentas.get(2).getCliente());
        System.out.println("Saldo anterior: 15000.0, Nuevo saldo: " + cuentas.get(2).getSaldo());
        
        System.out.println("\nCambio en la cuarta cuenta:");
        System.out.println("Cliente anterior: Maria Rodriguez, Nuevo cliente: " + cuentas.get(3).getCliente());
        System.out.println("Saldo anterior: 2000.0, Nuevo saldo: " + cuentas.get(3).getSaldo());
        
        System.out.println("\nCambio en la octava cuenta:");
        System.out.println("Cliente anterior: Sofia Herrera, Nuevo cliente: " + cuentas.get(7).getCliente());
        System.out.println("Saldo anterior: 6000.0, Nuevo saldo: " + cuentas.get(7).getSaldo());

        // 5. Imprimir nuevamente los toString de las cuentas
        System.out.println("\nCuentas despues de cambios:");
        for (Cuenta cuenta : cuentas) {
            System.out.println(cuenta.toString());
        }

        // 6. Realizar cuatro depositos en cualquiera de las cuentas
        try {
            cuentas.get(1).depositar(2000.0);
            cuentas.get(3).depositar(500.0);
            cuentas.get(5).depositar(10000.0);
            cuentas.get(7).depositar(3000.0);
        } catch (Exception e) {
            System.out.println("Error al realizar un deposito: " + e.getMessage());
        }

        // Imprimir depositos
        System.out.println("\nDepósitos realizados:");
        System.out.println("Deposito en cuenta 2: 2000.0");
        System.out.println("Deposito en cuenta 4: 500.0");
        System.out.println("Deposito en cuenta 6: 10000.0");
        System.out.println("Deposito en cuenta 8: 3000.0");

        // 7. Realizar cuatro retiros en cualquiera de las cuentas
        try {
            cuentas.get(0).retirar(5000.0);
            cuentas.get(2).retirar(2000.0);
            cuentas.get(4).retirar(1200.0);
            cuentas.get(6).retirar(800.0);
        } catch (Exception e) {
            System.out.println("Error al realizar un retiro: " + e.getMessage());
        }

        // Imprimir retiros
        System.out.println("\nRetiros realizados:");
        System.out.println("Retiro en cuenta 1: 5000.0");
        System.out.println("Retiro en cuenta 3: 200.0");
        System.out.println("Retiro en cuenta 5: 1200.0");
        System.out.println("Retiro en cuenta 7: 800.0");

        // 8. Imprimir nuevamente los toString de las cuentas
        System.out.println("\nCuentas despues de depositos y retiros:");
        for (Cuenta cuenta : cuentas) {
            System.out.println(cuenta.toString());
        }
    }
}
