package com.conversor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.conversor.modelo.Conversion;
import com.conversor.modelo.Moneda;
import com.conversor.servicio.ServicioAPI;
import com.conversor.util.Registrador;

/**
 * Clase principal del Conversor de Monedas
 */
public class Conversor {
    // Scanner para leer la entrada del usuario
    private Scanner scanner;
    
    // Servicio para obtener tasas de cambio
    private ServicioAPI servicioAPI;
    
    // Lista para almacenar el historial de conversiones
    private List<Conversion> historialConversiones;
    
    // Lista de monedas disponibles
    private List<Moneda> monedasDisponibles;
    
    /**
     * Constructor de la clase Conversor
     */
    public Conversor() {
        // Inicializar el scanner para leer la entrada del usuario
        this.scanner = new Scanner(System.in);
        
        // Inicializar el servicio de API
        this.servicioAPI = new ServicioAPI();
        
        // Inicializar la lista para el historial de conversiones
        this.historialConversiones = new ArrayList<>();
        
        // Inicializar y cargar las monedas disponibles
        this.monedasDisponibles = new ArrayList<>();
        cargarMonedasDisponibles();
        
        // Registrar inicio de la aplicación
        Registrador.registrarEvento("Aplicación iniciada");
    }
    
    /**
     * Método para cargar las monedas disponibles
     */
    private void cargarMonedasDisponibles() {
        // Agregar algunas monedas comunes
        monedasDisponibles.add(new Moneda("USD", "Dólar Estadounidense"));
        monedasDisponibles.add(new Moneda("EUR", "Euro"));
        monedasDisponibles.add(new Moneda("GBP", "Libra Esterlina"));
        monedasDisponibles.add(new Moneda("JPY", "Yen Japonés"));
        monedasDisponibles.add(new Moneda("CAD", "Dólar Canadiense"));
        monedasDisponibles.add(new Moneda("AUD", "Dólar Australiano"));
        monedasDisponibles.add(new Moneda("CHF", "Franco Suizo"));
        monedasDisponibles.add(new Moneda("CNY", "Yuan Chino"));
        monedasDisponibles.add(new Moneda("MXN", "Peso Mexicano"));
        monedasDisponibles.add(new Moneda("BRL", "Real Brasileño"));
    }
    
    /**
     * Método para iniciar el conversor de monedas
     */
    public void iniciar() {
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenu();
            int opcion = obtenerOpcionUsuario();
            
            switch (opcion) {
                case 1:
                    realizarConversion("USD", "EUR");
                    break;
                case 2:
                    realizarConversion("USD", "GBP");
                    break;
                case 3:
                    realizarConversion("USD", "JPY");
                    break;
                case 4:
                    realizarConversion("EUR", "USD");
                    break;
                case 5:
                    realizarConversion("GBP", "USD");
                    break;
                case 6:
                    realizarConversion("JPY", "USD");
                    break;
                case 7:
                    realizarConversionPersonalizada();
                    break;
                case 8:
                    mostrarHistorialConversiones();
                    break;
                case 9:
                    mostrarMonedasDisponibles();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("¡Gracias por usar el Conversor de Monedas!");
                    Registrador.registrarEvento("Aplicación finalizada");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    Registrador.registrarEvento("Opción inválida seleccionada: " + opcion);
            }
        }
        
        // Cerrar el scanner al finalizar
        scanner.close();
    }
    
    /**
     * Método para mostrar el menú principal
     */
    private void mostrarMenu() {
        System.out.println("\n===== CONVERSOR DE MONEDAS =====");
        System.out.println("Sea bienvenido/a al Conversor de Moneda");
        System.out.println("1 - Convertir de Dólar a Euro");
        System.out.println("2 - Convertir de Dólar a Libra Esterlina");
        System.out.println("3 - Convertir de Dólar a Yen Japonés");
        System.out.println("4 - Convertir de Euro a Dólar");
        System.out.println("5 - Convertir de Libra Esterlina a Dólar");
        System.out.println("6 - Convertir de Yen Japonés a Dólar");
        System.out.println("7 - Conversión personalizada");
        System.out.println("8 - Ver historial de conversiones");
        System.out.println("9 - Ver monedas disponibles");
        System.out.println("0 - Salir");
        System.out.println("Elija una opción válida:");
    }
    
    /**
     * Método para obtener la opción seleccionada por el usuario
     */
    private int obtenerOpcionUsuario() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            Registrador.registrarError("DEBE INGRESAR UN NUMERO", e);
            return -1; // Valor inválido
        }
    }
    
    /**
     * Método para realizar una conversión entre dos monedas
     */
    private void realizarConversion(String monedaOrigen, String monedaDestino) {
        System.out.println("\nConvirtiendo de " + monedaOrigen + " a " + monedaDestino);
        System.out.println("Ingrese la cantidad a convertir:");
        
        try {
            double cantidad = Double.parseDouble(scanner.nextLine());
            
            // Registrar intento de conversión
            Registrador.registrarEvento("Intento de conversión: " + cantidad + " " + monedaOrigen + " a " + monedaDestino);
            
            // Obtener la tasa de cambio
            double tasaCambio = servicioAPI.obtenerTasaCambio(monedaOrigen, monedaDestino);
            
            // Realizar la conversión
            double resultado = cantidad * tasaCambio;
            
            // Mostrar el resultado
            System.out.printf("%.2f %s = %.2f %s\n", cantidad, monedaOrigen, resultado, monedaDestino);
            
            // Registrar la conversión en el historial
            registrarConversion(cantidad, monedaOrigen, resultado, monedaDestino);
            
            // Registrar conversión exitosa
            Registrador.registrarEvento("Conversión exitosa: " + cantidad + " " + monedaOrigen + " = " + resultado + " " + monedaDestino);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingrese un  numéro válido.");
            Registrador.registrarError("Error de formato numérico en la cantidad", e);
        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
            Registrador.registrarError("Error al realizar la conversión", e);
        }
    }
    
    /**
     * Método para realizar una conversión personalizada
     */
    private void realizarConversionPersonalizada() {
        System.out.println("\n===== CONVERSIÓN PERSONALIZADA =====");
        
        System.out.println("Ingrese el código de la moneda de origen (ej. USD, EUR, GBP):");
        String monedaOrigen = scanner.nextLine().toUpperCase();
        
        System.out.println("Ingrese el código de la moneda de destino (ej. USD, EUR, GBP):");
        String monedaDestino = scanner.nextLine().toUpperCase();
        
        realizarConversion(monedaOrigen, monedaDestino);
    }
    
    /**
     * Método para registrar una conversión en el historial
     */
    private void registrarConversion(double cantidadOrigen, String monedaOrigen, 
                                    double cantidadDestino, String monedaDestino) {
        // Crear un nuevo objeto de conversión
        Conversion conversion = new Conversion(cantidadOrigen, monedaOrigen, 
                                              cantidadDestino, monedaDestino);
        
        // Agregar al historial
        historialConversiones.add(conversion);
        
        System.out.println("Conversión registrada en el historial.");
    }
    
    /**
     * Método para mostrar el historial de conversiones
     */
    private void mostrarHistorialConversiones() {
        System.out.println("\n===== HISTORIAL DE CONVERSIONES =====");
        
        if (historialConversiones.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            System.out.println("Últimas conversiones realizadas:");
            for (int i = historialConversiones.size() - 1; i >= 0; i--) {
                System.out.println(historialConversiones.get(i));
            }
        }
    }
    
    /**
     * Método para mostrar las monedas disponibles
     */
    private void mostrarMonedasDisponibles() {
        System.out.println("\n===== MONEDAS DISPONIBLES =====");
        
        for (Moneda moneda : monedasDisponibles) {
            System.out.println(moneda);
        }
        
        System.out.println("\nNota: También puede utilizar otros códigos de moneda válidos en la conversión personalizada.");
    }
}
