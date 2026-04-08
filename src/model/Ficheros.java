/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Ficheros {
    
  public static void writeSales(ArrayList<Sale> sales) {

    LocalDate date = LocalDate.now();
    String fileName = "sales_" + date + ".txt";

    FileWriter outputStreamProduct = null;
    BufferedWriter out1 = null;

    try {
        outputStreamProduct = new FileWriter(fileName);
        out1 = new BufferedWriter(outputStreamProduct);

        int i = 1;

        for (Sale sale : sales) {

            // CLIENTE
            out1.write(i + ";Client=" + sale.getClient().getName() + ";\n");

            // PRODUCTS (SIN CORCHETES)
            String productsText = i + ";Products=";
            for (Product p : sale.getProducts()) {
                productsText += p.getName() + "," + p.getPublicPrice().getValue() + "?;";
            }
            out1.write(productsText + "\n");

            // AMOUNT
            out1.write(i + ";Amount=" + sale.getAmount().getValue() + "?;\n");

            i++;
        }

    } catch (IOException ex) {
        System.out.println("Error al escribir el archivo");

    } finally {
        if (out1 != null) {
            try {
                out1.close();
            } catch (IOException ex) {
                System.out.println("No se puede acceder al archivo.");
            }
        }
    }
}
  
public static void LecturaFichero(ArrayList<Product> inventory){
    File myobj = new File ("inputinventory.txt");
    String linea;
 
        String nombre = null;
 
        Amount wholesalerprice = new Amount(0.0);
 
        int stock = 0;
 
        FileReader inputStream = null;
        BufferedReader in = null;
 
        try {
            inputStream = new FileReader("inputInventory.txt");
            in = new BufferedReader(inputStream);
 
            while ((linea = in.readLine()) != null) {
 
                String datos[] = linea.split(";");
 
                for (int i = 0; i < datos.length; i ++) {
                    String finalDatos[] = datos[i].split(":");
                   
                    if (finalDatos[0].equals("Product")) {
                        nombre = finalDatos[1];
                    } else if (finalDatos[0].equals("Wholesaler Price")) {
                        wholesalerprice = new Amount(Double.parseDouble(finalDatos[1]));
                    } else if (finalDatos[0].equals("Stock")) {
                        stock = Integer.parseInt(finalDatos[1]);
 
                    }
 
                }
                Product p = new Product(nombre, wholesalerprice, true, stock);
               inventory.add(p);
            }
 
        } catch (java.io.IOException ex) {
            System.out.println(ex);
            System.out.println("No se puede acceder al archivo.");
        }  finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    System.out.println("No se puede acceder al archivo.");
                }
            }
        }
 
    }
    public static boolean findproduct(String nombre){
        String linea;
        FileReader inputStream = null;
        BufferedReader in = null;
        try {
            inputStream = new FileReader("inputInventory.txt");
            in = new BufferedReader(inputStream);
            while((linea = in.readLine()) != null){
                String datos[] = linea.split(";");
                for(int i = 0; i < datos.length; i++){
                    String finalDatos[] = datos[i].split(":");
                    if(finalDatos[0].equals("Product")){
                        if(finalDatos[1].equalsIgnoreCase(nombre)){
                            return true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("No se puede acceder al archivo.");
        } finally {
            if(in != null){
                try{
                    in.close();
                } catch(IOException ex){
                    System.out.println("No se puede cerrar el archivo.");
                }
            }
        }
        return false;
    }
  
}


