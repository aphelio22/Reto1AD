package org.example;

import java.io.*;
import java.util.ArrayList;

/**
 * La clase 'Lectura' proporciona métodos para leer un archivo '.csv' con datos de usuarios,
 * leer una plantilla y reemplazar los marcadores de esta última por los datos del '.csv'
 * generando una plantilla distinta para cada fila de datos.
 * */
public class Lectura {

    /**
     * Lee un archivo '.csv', procesa sus datos y genera archivos de salida basados en una plantilla leída
     * por medio de un método, 'readTemplate()'.
     *
     * @param clientData El archivo '.csv' que contiene los datos del cliente.
     * @param template   El archivo de plantilla que se utilizará para generar los archivos de salida.
     * @throws RuntimeException Si se producen errores durante la lectura, procesamiento o escritura de archivos.
     */
    public void leerArchivos(String clientData, String template){

        try (BufferedReader br = new BufferedReader(new FileReader(clientData))) {

            //Variable para gestionar la carpeta de 'salida'.
            File salida = new File("salida");

            if (salida.exists() && salida.isDirectory()) {
                //Limpia la carpeta de salida de archivos existentes, si es que hay, en cada ejecución.
                for (File file : salida.listFiles()) {
                    file.delete();
                }
            }

            //Crea la carpeta de 'salida'.
            salida.mkdir();
            //Lee el contenido de la plantilla llamando al método 'readTemplate()' y lo almacena en 'readedTemplate'.
            String readedTemplate = readTemplate(template);

            String rc;
            //Bucle que se repite por cada fila del '.csv'.
            while ((rc = br.readLine()) != null){
                /*Divide cada fila del archivo '.csv' en datos separados por comas
                 y los almacena en una posición de un array de Strings siempre que cada fila contenga 5 datos.*/
                String[] datos = rc.split(",");
                    if (datos.length == 5) {
                        ArrayList<String> templates = new ArrayList<>();
                        String id = datos[0];
                        String nombreCompanhia = datos[1];
                        String ciudad = datos[2];
                        String email = datos[3];
                        String nombreContacto = datos[4];

                        // Copia la plantilla leída
                        String rp = readedTemplate;

                        // Reemplaza los marcadores en cada plantilla con los datos del array.
                        rp = rp.replace("%%2%%", nombreCompanhia);
                        rp = rp.replace("%%3%%", ciudad);
                        rp = rp.replace("%%4%%", email);
                        rp = rp.replace("%%5%%", nombreContacto);

                        // Variable para gestionar la escritura de archivos de salida.
                        BufferedWriter bwt;
                        if (readedTemplate.contains("%%2%%") && readedTemplate.contains("%%3%%") &&
                                readedTemplate.contains("%%4%%") && readedTemplate.contains("%%5%%")) {

                            // Agrega cada plantilla procesada al Arraylist 'templates'.
                            templates.add(rp);

                            // Escribe cada plantilla en la carpeta 'salida'.
                            bwt = new BufferedWriter(new FileWriter("salida/template-" + id));
                            for (String finalTemplate : templates) {
                                bwt.write(finalTemplate);
                                bwt.flush();
                            }

                        } else {
                            // Si la plantilla no contiene todos los marcadores necesarios, genera un archivo de aviso de error.
                            bwt = new BufferedWriter(new FileWriter("salida/aviso_error"));
                            rp = "Error en el formato, por favor compruebe el archivo: " + template;
                            bwt.write(rp);
                            bwt.flush();
                        }
                    } else {
                        // Si la línea del '.csv' no tiene la cantidad esperada de datos, muestra un mensaje de error en la consola.
                        System.out.println("Error en uno de los templates por falta o exceso de datos en el csv.");
                        System.out.println("Usuario del csv: " + rc);
                        System.out.println("No se generará el template relacionado con los datos de ese usuario.");
                        System.out.println("--------------------------------------------");
                    }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lee el contenido de un archivo de plantilla y lo devuelve como una cadena.
     *
     * @param templateFile El archivo de plantilla a leer.
     * @return El contenido del archivo de plantilla como una cadena.
     * @throws RuntimeException Si se producen errores durante la lectura del archivo de plantilla.
     */
    private static String readTemplate(String templateFile) {
        // Variable para almacenar el contenido del archivo de la plantilla.
        StringBuilder template = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(templateFile))) {
            String s;
            while ((s = br.readLine()) != null) {
                // Agrega cada línea del archivo de la plantilla a la cadena de plantilla.
                template.append(s).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return template.toString();
    }
}
