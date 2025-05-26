package com.conversor.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una conversión en el historial
 */
public class Conversion {
    private double cantidadOrigen;
    private String monedaOrigen;
    private double cantidadDestino;
    private String monedaDestino;
    private LocalDateTime fechaHora;
    
    /**
     * Constructor de la clase Conversion
     */
    public Conversion(double cantidadOrigen, String monedaOrigen, 
                     double cantidadDestino, String monedaDestino) {
        this.cantidadOrigen = cantidadOrigen;
        this.monedaOrigen = monedaOrigen;
        this.cantidadDestino = cantidadDestino;
        this.monedaDestino = monedaDestino;
        this.fechaHora = LocalDateTime.now();
    }
    
    /**
     * Obtiene la cantidad de origen
     */
    public double getCantidadOrigen() {
        return cantidadOrigen;
    }
    
    /**
     * Obtiene la moneda de origen
     */
    public String getMonedaOrigen() {
        return monedaOrigen;
    }
    
    /**
     * Obtiene la cantidad de destino
     */
    public double getCantidadDestino() {
        return cantidadDestino;
    }
    
    /**
     * Obtiene la moneda de destino
     */
    public String getMonedaDestino() {
        return monedaDestino;
    }
    
    /**
     * Obtiene la fecha y hora de la conversión
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    /**
     * Obtiene la fecha y hora formateada
     */
    public String getFechaHoraFormateada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaHora.format(formato);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %.2f %s → %.2f %s", 
                getFechaHoraFormateada(), cantidadOrigen, monedaOrigen, 
                cantidadDestino, monedaDestino);
    }
}
