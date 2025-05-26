package com.conversor.modelo;

/**
 * Clase que representa una moneda en el sistema
 */
public class Moneda {
    private String codigo;
    private String nombre;
    
    /**
     * Constructor de la clase Moneda
     */
    public Moneda(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el código de la moneda
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     * Obtiene el nombre de la moneda
     */
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
