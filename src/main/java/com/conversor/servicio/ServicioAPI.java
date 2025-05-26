package com.conversor.servicio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Clase que maneja las solicitudes a la API de tasas de cambio
 */
public class ServicioAPI {
    // API Key para ExchangeRate-API
    private final String API_KEY = "5e930c67be5dc3ea26711926";
    private final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    
    // Cliente HTTP para realizar las solicitudes
    private HttpClient httpClient;
    
    /**
     * Constructor de la clase ServicioAPI
     */
    public ServicioAPI() {
        // Inicializar el cliente HTTP con un timeout de 10 segundos
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
    
    /**
     * Método para obtener la tasa de cambio entre dos monedas
     */
    public double obtenerTasaCambio(String monedaOrigen, String monedaDestino) throws Exception {
        // Construir la URL para la solicitud
        String url = BASE_URL + API_KEY + "/latest/" + monedaOrigen;
        
        // Crear la solicitud HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        // Enviar la solicitud y obtener la respuesta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Verificar el código de estado de la respuesta
        if (response.statusCode() == 200) {
            // Analizar la respuesta JSON
            String jsonResponse = response.body();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            
            // Verificar si la respuesta es exitosa
            String resultado = jsonObject.get("result").getAsString();
            
            if ("success".equals(resultado)) {
                // Obtener las tasas de conversión
                JsonObject tasas = jsonObject.getAsJsonObject("conversion_rates");
                
                // Verificar si la moneda de destino existe en las tasas
                if (tasas.has(monedaDestino)) {
                    return tasas.get(monedaDestino).getAsDouble();
                } else {
                    throw new Exception("La moneda de destino '" + monedaDestino + "' no está disponible.");
                }
            } else {
                throw new Exception("Error en la respuesta de la API: " + resultado);
            }
        } else {
            throw new Exception("Error en la solicitud HTTP: " + response.statusCode());
        }
    }
}
