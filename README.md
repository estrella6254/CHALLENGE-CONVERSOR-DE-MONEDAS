# Informe del Challenge: Conversor de Monedas en Java

## Introducción

Este informe detalla el desarrollo de un Conversor de Monedas implementado en Java, como parte del Challenge de la formacion ----> Java Orientado a Objetos, siguiendo las fases establecidas en la plataforma TRELLO para su implementacion. El proyecto ha sido desarrollado utilizando variables y estructuras básicas, pero asegurando que la aplicación funcione correctamente.

El Conversor de Monedas permite realizar conversiones entre diferentes divisas utilizando tasas de cambio en tiempo real obtenidas a través de la API ExchangeRate-API. Además de las funcionalidades básicas de conversión, se han implementado características adicionales como el historial de conversiones, soporte para múltiples monedas y registros con marca de tiempo.

## Estructura del Proyecto

El proyecto sigue una estructura organizada por paquetes, lo que facilita la comprensión y mantenimiento del código:

```
ConversorMonedas/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── conversor/
│                   ├── Main.java
│                   ├── Conversor.java
│                   ├── modelo/
│                   │   ├── Moneda.java
│                   │   └── Conversion.java
│                   ├── servicio/
│                   │   └── ServicioAPI.java
│                   └── util/
│                       └── Registrador.java
```

Esta estructura separa claramente las responsabilidades:
- La clase principal (`Main.java`) inicia la aplicación
- La clase `Conversor.java` maneja la lógica principal y la interfaz de usuario
- El paquete `modelo` contiene las clases que representan entidades (Moneda, Conversion)
- El paquete `servicio` contiene la clase que interactúa con la API externa
- El paquete `util` contiene utilidades como el registrador de eventos

## Explicación Detallada del Código

### Main.java

```java
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
```

**Función**: Esta clase es el punto de entrada de la aplicación. Su única responsabilidad es crear una instancia de la clase `Conversor` e iniciar la ejecución del programa.

### Conversor.java

La clase `Conversor` es el núcleo de la aplicación y contiene la lógica principal para la interacción con el usuario y la coordinación de las conversiones.

**Funciones principales**:

1. **iniciar()**: Método principal que muestra el menú y maneja el flujo de la aplicación en un bucle hasta que el usuario decide salir.

2. **mostrarMenu()**: Muestra las opciones disponibles para el usuario.

3. **obtenerOpcionUsuario()**: Lee y valida la opción seleccionada por el usuario.

4. **realizarConversion(String monedaOrigen, String monedaDestino)**: Solicita al usuario la cantidad a convertir, obtiene la tasa de cambio a través del servicio API, realiza la conversión y registra el resultado en el historial.

5. **realizarConversionPersonalizada()**: Permite al usuario especificar las monedas de origen y destino para una conversión personalizada.

6. **registrarConversion()**: Crea un nuevo objeto de tipo `Conversion` y lo añade al historial.

7. **mostrarHistorialConversiones()**: Muestra las conversiones realizadas previamente, ordenadas de la más reciente a la más antigua.

8. **mostrarMonedasDisponibles()**: Muestra la lista de monedas disponibles para la conversión.

### Modelo

#### Moneda.java

```java
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
```

**Función**: Esta clase representa una moneda en el sistema, con su código (por ejemplo, "USD") y su nombre completo (por ejemplo, "Dólar Estadounidense"). Proporciona métodos para acceder a estos datos y una representación en texto para mostrar al usuario.

#### Conversion.java

```java
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
    
    // Métodos getter y toString()...
}
```

**Función**: Esta clase representa una conversión realizada por el usuario, almacenando la cantidad y moneda de origen, la cantidad y moneda de destino, y la fecha y hora en que se realizó la conversión. Implementa la funcionalidad de "registros con marca de tiempo" solicitada.

### Servicio

#### ServicioAPI.java

```java
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
        // Implementación...
    }
}
```

**Función**: Esta clase encapsula la lógica de comunicación con la API de tasas de cambio. Utiliza las clases `HttpClient`, `HttpRequest` y `HttpResponse` de Java para realizar solicitudes HTTP y procesar las respuestas JSON utilizando la biblioteca Gson.

### Utilidades

#### Registrador.java

```java
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
        // Implementación...
    }
}
```

**Función**: Esta clase proporciona funcionalidades para registrar eventos y errores en un archivo de log, implementando la funcionalidad de "registros con marca de tiempo" solicitada.

## Implementación de las Fases del Proyecto

### Fase 4: Uso de HttpClient

En esta fase, se implementó el uso de la clase `HttpClient` para realizar solicitudes a la API de tasas de cambio. Esta clase se utiliza en `ServicioAPI.java` para establecer la conexión con la API:

```java
this.httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();
```

El cliente HTTP se configura con un tiempo de espera (timeout) de 10 segundos para evitar bloqueos indefinidos en caso de problemas de conexión.

### Fase 5: Uso de HttpRequest

En esta fase, se implementó el uso de la clase `HttpRequest` para configurar las solicitudes a la API. En `ServicioAPI.java`, se crea una solicitud HTTP GET:

```java
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();
```

La solicitud se configura con la URL de la API y el método GET para obtener los datos de las tasas de cambio.

### Fase 6: Uso de HttpResponse

En esta fase, se implementó el uso de la interfaz `HttpResponse` para gestionar las respuestas recibidas de la API. En `ServicioAPI.java`, se envía la solicitud y se procesa la respuesta:

```java
HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

if (response.statusCode() == 200) {
    // Procesar respuesta exitosa
} else {
    // Manejar error
}
```

Se verifica el código de estado de la respuesta para determinar si la solicitud fue exitosa (código 200) o si ocurrió algún error.

### Fase 7: Análisis de Respuesta JSON con Gson

En esta fase, se implementó el análisis de la respuesta JSON utilizando la biblioteca Gson. En `ServicioAPI.java`, se analiza la respuesta JSON para extraer las tasas de cambio:

```java
String jsonResponse = response.body();
JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

String resultado = jsonObject.get("result").getAsString();

if ("success".equals(resultado)) {
    JsonObject tasas = jsonObject.getAsJsonObject("conversion_rates");
    // Obtener tasa específica
}
```

Se utiliza `JsonParser` para convertir la cadena JSON en un objeto `JsonObject`, y luego se navega por la estructura para acceder a los datos necesarios.

### Fase 8: Filtrado de Monedas

En esta fase, se implementó el filtrado de monedas utilizando la biblioteca Gson. En `ServicioAPI.java`, se verifica si la moneda de destino existe en las tasas de conversión:

```java
if (tasas.has(monedaDestino)) {
    return tasas.get(monedaDestino).getAsDouble();
} else {
    throw new Exception("La moneda de destino '" + monedaDestino + "' no está disponible.");
}
```

Se utiliza el método `has()` para verificar si la moneda existe en el objeto JSON, y `get().getAsDouble()` para obtener el valor de la tasa de cambio.

### Fase 9: Conversión de Monedas

En esta fase, se implementó la lógica de conversión utilizando las tasas obtenidas. En `Conversor.java`, se realiza la conversión:

```java
double tasaCambio = servicioAPI.obtenerTasaCambio(monedaOrigen, monedaDestino);
double resultado = cantidad * tasaCambio;
```

La conversión es una simple multiplicación de la cantidad por la tasa de cambio.

### Fase 10: Interfaz de Usuario por Consola

En esta fase, se implementó la interfaz de usuario por consola que permite al usuario interactuar con el conversor. En `Conversor.java`, se muestra un menú con opciones y se procesa la entrada del usuario:

```java
mostrarMenu();
int opcion = obtenerOpcionUsuario();

switch (opcion) {
    case 1:
        realizarConversion("USD", "EUR");
        break;
    // Más casos...
}
```

La interfaz permite al usuario seleccionar entre conversiones predefinidas, realizar conversiones personalizadas, ver el historial de conversiones y ver las monedas disponibles.

## Funcionalidades Adicionales

### Historial de Conversiones

Se implementó un historial de conversiones que almacena todas las conversiones realizadas por el usuario. Cada conversión incluye la cantidad y moneda de origen, la cantidad y moneda de destino, y la fecha y hora en que se realizó.

```java
private List<Conversion> historialConversiones = new ArrayList<>();

private void registrarConversion(double cantidadOrigen, String monedaOrigen, 
                                double cantidadDestino, String monedaDestino) {
    Conversion conversion = new Conversion(cantidadOrigen, monedaOrigen, 
                                          cantidadDestino, monedaDestino);
    historialConversiones.add(conversion);
}
```

El usuario puede ver el historial seleccionando la opción correspondiente en el menú.

### Soporte para Más Monedas

Se implementó soporte para múltiples monedas, permitiendo al usuario realizar conversiones entre cualquier par de monedas soportadas por la API. Además de las conversiones predefinidas, se ofrece una opción de "Conversión personalizada" donde el usuario puede especificar las monedas de origen y destino.

```java
private void realizarConversionPersonalizada() {
    System.out.println("Ingrese el código de la moneda de origen:");
    String monedaOrigen = scanner.nextLine().toUpperCase();
    
    System.out.println("Ingrese el código de la moneda de destino:");
    String monedaDestino = scanner.nextLine().toUpperCase();
    
    realizarConversion(monedaOrigen, monedaDestino);
}
```

También se incluye una lista de monedas comunes que el usuario puede consultar.

### Registros con Marca de Tiempo

Se implementaron dos tipos de registros con marca de tiempo:

1. **Historial de conversiones**: Cada conversión en el historial incluye la fecha y hora en que se realizó.

```java
public class Conversion {
    private LocalDateTime fechaHora;
    
    public Conversion(...) {
        this.fechaHora = LocalDateTime.now();
    }
}
```

2. **Archivo de log**: Se implementó un registrador que guarda eventos y errores en un archivo de log, incluyendo la fecha y hora de cada registro.

```java
public class Registrador {
    private static void registrar(String tipo, String mensaje) {
        LocalDateTime ahora = LocalDateTime.now();
        String fechaHora = ahora.format(FORMATO_FECHA);
        String lineaLog = String.format("[%s] [%s] %s", fechaHora, tipo, mensaje);
        
        // Escribir en archivo
    }
}
```

## Diagrama de Flujo de Datos

A continuación se presenta un diagrama de flujo de datos que muestra cómo fluye la información a través del sistema:

```
+----------------+     +----------------+     +----------------+
|                |     |                |     |                |
|    Usuario     |---->|    Conversor   |---->|  ServicioAPI   |
|                |<----|                |<----|                |
+----------------+     +----------------+     +----------------+
                              |  ^
                              |  |
                              v  |
+----------------+     +----------------+     +----------------+
|                |     |                |     |                |
|    Moneda      |<----|  Conversion    |---->|  Registrador   |
|                |     |                |     |                |
+----------------+     +----------------+     +----------------+
```

**Descripción del flujo**:

1. El usuario interactúa con la clase `Conversor` a través de la consola, seleccionando opciones del menú y proporcionando datos para las conversiones.

2. La clase `Conversor` utiliza `ServicioAPI` para obtener las tasas de cambio actualizadas de la API externa.

3. `ServicioAPI` realiza solicitudes HTTP a la API ExchangeRate-API y procesa las respuestas JSON.

4. Con las tasas obtenidas, `Conversor` realiza los cálculos de conversión y crea objetos `Conversion` para registrar en el historial.

5. Los objetos `Conversion` contienen referencias a las monedas involucradas (representadas por la clase `Moneda`).

6. El `Registrador` se utiliza para guardar eventos y errores en un archivo de log.

## Conclusiones

El Conversor de Monedas desarrollado cumple con todos los requisitos establecidos en las fases de desarrollo establecidas en TRELLO, implementando correctamente el uso de `HttpClient`, `HttpRequest`, `HttpResponse` y la biblioteca Gson para interactuar con la API de tasas de cambio. Además, se implementaron las funcionalidades adicionales : historial de conversiones, soporte para más monedas y registros con marca de tiempo.

La aplicación sigue una estructura organizada que separa  las responsabilidades, lo que facilita su comprensión y mantenimiento, el código implementa buenas prácticas como la encapsulación, la separación de responsabilidades y el manejo adecuado de errores.

La silucion a este Challenge demuestra el uso efectivo de las bibliotecas estándar de Java para realizar solicitudes HTTP y procesar respuestas JSON, así como la implementación de una interfaz de usuario por consola que permite al usuario interactuar con la aplicación de manera intuitiva.
