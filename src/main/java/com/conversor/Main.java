package com.conversor;

/**
 * Clase principal que inicia la aplicación del Conversor de Monedas
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Conversor de Monedas...");
        
        // Crear instancia del conversor y mostrar el menú
        Conversor conversor = new Conversor();
        conversor.iniciar();
    }
}
