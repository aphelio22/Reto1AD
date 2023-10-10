package org.example;

/**
 * La clase 'Main' es la clase principal de la aplicación. Contiene el método 'main', que inicia la ejecución del programa.
 */
public class Main {

    /**
     * Método principal que inicia la ejecución del programa.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        // Crea una instancia de la clase 'Lectura'
        Lectura lectura1 = new Lectura();

        // Llama al método 'leerArchivos' de la instancia 'lectura1' para procesar el '.csv' y la plantilla.
        lectura1.leerArchivos("data.csv", "plantilla.txt");
    }
}