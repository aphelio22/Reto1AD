package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lectura {

    public void leerArchivos(String clientes, String plantilla){

        try (BufferedReader br = new BufferedReader(new FileReader(clientes))) {
            String rc;
            String plantillaTexto = readTemplate(plantilla);
            while ((rc = br.readLine()) != null){
                String[] datos = rc.split(",");
                if (datos.length >= 5){
                    ArrayList<String> templates= new ArrayList<>();
                    String id = datos[0];
                    String nombreCompanhia = datos[1];
                    String ciudad= datos[2];
                    String email = datos[3];
                    String nombreContacto = datos[4];

                    String rp = plantillaTexto;

                        rp = rp.replace("%%1%%", id);
                        rp = rp.replace("%%2%%", nombreCompanhia);
                        rp = rp.replace("%%3%%", ciudad);
                        rp = rp.replace("%%4%%", email);
                        rp = rp.replace("%%5%%", nombreContacto);

                        templates.add(rp);

                    File salida = new File("salida");
                    salida.mkdir();
                    BufferedWriter bwt = new BufferedWriter(new FileWriter("salida/template-"+id));
                    for(String finalTemplate : templates){
                        bwt.write(finalTemplate);
                        bwt.flush();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String readTemplate(String templateFile) {
        StringBuilder template = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(templateFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                template.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return template.toString();
    }
}
