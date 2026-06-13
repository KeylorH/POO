package tareas.tarea1;

/**
 *
 * @author pino1
 */
import java.time.LocalDateTime;
class Cuenta {
    private static int cantidadCuentas = 0;
    private int numero;
    private String cliente;
    private double saldo;
    private LocalDateTime fechaCreacion;

    // Constructor con saldo inicial
    public Cuenta(String cliente, double saldo) {
        this.numero = ++cantidadCuentas;
        this.cliente = cliente;
        this.saldo = saldo;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor sin saldo inicial (saldo inicializado en cero)
    public Cuenta(String cliente) {
        this(cliente, 0.0);
    }

    // Metodos get y set
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    // Metodo para depositar dinero
    public void depositar(double monto) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto a depositar debe ser positivo.");
        }
        saldo += monto;
    }

    // Metodo para retirar dinero
    public void retirar(double monto) throws Exception {
        if (monto <= 0 || monto > saldo) {
            throw new Exception("El monto a retirar debe ser positivo y no puede superar el saldo actual.");
        }
        saldo -= monto;
    }

    // Metodo para obtener una representacion en cadena de la cuenta
    @Override
    public String toString() {
        return "Cuenta numero: " + numero +
                "\nCliente: " + cliente +
                "\nSaldo: " + saldo +
                "\nFecha de creacion: " + fechaCreacion;
    }
}


