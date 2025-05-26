package com.conversor.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase utilitaria para registrar eventos y errores
 */
public class Registrador {
    private static final String ARCHIVO_LOG = "conversor_log.txt";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Registra un evento en el archivo de log
     */
    public static void registrarEvento(String mensaje) {
        registrar("INFO", mensaje);
    }
    
    /**
     * Registra un error en el archivo de log
     */
    public static void registrarError(String mensaje, Exception e) {
        registrar("ERROR", mensaje + ": " + e.getMessage());
    }
    
    /**
     * Método privado para registrar en el archivo
     */
    private static void registrar(String tipo, String mensaje) {
        LocalDateTime ahora = LocalDateTime.now();
        String fechaHora = ahora.format(FORMATO_FECHA);
        String lineaLog = String.format("[%s] [%s] %s", fechaHora, tipo, mensaje);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_LOG, true))) {
            writer.println(lineaLog);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }
}
