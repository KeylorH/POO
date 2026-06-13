package tareas.tarea3keylorherrerafuentes;

import java.io.Serializable;

/**
 * Tarea #4 Programación Orientada a Objetos.
 * Estudiantes: Keylor Herrera Fuentes (2021046723) Samantha Tames Piedra (2022207855)
 */
// Clase Producto

public class Producto  implements Serializable {

    private static int consecutivo = 1;
    private final int codigo;
    private final String nombre;
    public float precio;
    private final String unidad;
    private float existencias;

    public Producto(String nombre, float precio, String unidad, float existencias) {
        this.codigo = consecutivo++;
        this.nombre = nombre;
        this.precio = precio;
        this.unidad = unidad;
        this.existencias = existencias;
    }
    
    // Métodos getter para acceder a los atributos privados
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public String getUnidad() {
        return unidad;
    }

    public float getExistencias() {
        return existencias;
    }
    public void retirarExistencias(float cantidad) {
        if (cantidad <= existencias) {
            existencias -= cantidad;
            System.out.println("Se retiraron " + cantidad + " unidades de existencias del producto " + nombre);
        } else {
            System.out.println("No hay suficientes existencias del producto " + nombre);
        }
    }

    public void agregarExistencias(float cantidad) {
        existencias += cantidad;
        System.out.println("Se agregaron " + cantidad + " unidades de existencias al producto " + nombre);
    }
    
    @Override
    public String toString() {
        return "Producto" +
                " código=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio= $" + precio +
                ", unidad='" + unidad + '\'' +
                ", existencias=" + existencias ;
    }
}