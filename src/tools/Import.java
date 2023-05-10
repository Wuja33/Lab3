package tools;

import models.Order;
import models.Payment;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public abstract class Import {

    public static List<List<String>> createListofParametersFromFile(String filePath, String separator, boolean showError) {
        String s;
        List<List<String>> listOfLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((s = br.readLine()) != null) {
                List<String> line = new ArrayList<>();
                List<String> listStringsFromLine = Arrays.asList(s.split(separator)); //zapisz do listy stringi, oddzielone separatorem
                for (String x : listStringsFromLine) { //robienie listy parametrów z jednej linijki
                    line.add(x);
                }
                listOfLines.add(line);  //dodanie listy parametrów, do spisu linii
            }
        }
        catch (IOException e) {
            if (showError)
                System.out.println("Nie znaleziono pliku");
        }
        return listOfLines;
    }
    public static void appendNewElements(List<Order> orders, String filePath)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
        PrintWriter printWriter = new PrintWriter(writer))
        {
            for (Order order :
                    orders) {
                printWriter.print(order.getId());
                printWriter.print(order.listToString());
                printWriter.println(order.getStatus());
            }
        }
        catch (IOException e)
        {
            System.out.println("Błąd podczas dodawania zamówień do pliku.");
        }
    }
    public static void appendNewElement(Order order, String filePath)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
            PrintWriter printWriter = new PrintWriter(writer))
        {
                printWriter.print(order.getId());
                printWriter.print(";");
                printWriter.print(order.listToString());
                printWriter.print(";");
                printWriter.print(order.getStatus());
                printWriter.print(";");
                printWriter.println(String.format(Locale.ENGLISH ,"%.2f",order.getPrice()));
        }
        catch (IOException e)
        {
            System.out.println("Błąd podczas dodawania zamówień do pliku.");
        }
    }
    public static void appendNewElement(Payment payment, String filePath)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
            PrintWriter printWriter = new PrintWriter(writer))
        {
            printWriter.print(payment.getOrder().getId());
            printWriter.print(";");
            printWriter.print(payment.getSystem());
            printWriter.print(";");
            printWriter.print(payment.getStatus());
            printWriter.print(";");
            printWriter.println(String.format("%.2f",payment.getPrice()));
        }
        catch (IOException e)
        {
            System.out.println("Błąd podczas dodawania płatności do pliku.");
        }
    }
    public static void appendNewElement2(Payment payment, String filePath)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false));
            PrintWriter printWriter = new PrintWriter(writer))
        {
            printWriter.print(payment.getOrder().getId());
            printWriter.print(";");
            printWriter.print(payment.getSystem());
            printWriter.print(";");
            printWriter.print(payment.getStatus());
            printWriter.print(";");
            printWriter.println(String.format("%.2f",payment.getPrice()));
        }
        catch (IOException e)
        {
            System.out.println("Błąd podczas dodawania płatności do pliku.");
        }
    }
    public static void createFileOrders(Order order, String filePath)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false));
            PrintWriter printWriter = new PrintWriter(writer))
        {
            printWriter.print(order.getId());
            printWriter.print(";");
            printWriter.print(order.listToString());
            printWriter.print(";");
            printWriter.print(order.getStatus());
            printWriter.print(";");
            printWriter.println(String.format(Locale.ENGLISH,"%.2f",order.getPrice()));
        }
        catch (IOException e)
        {
            System.out.println("Błąd podczas dodawania zamówień do pliku.");
        }
    }

}
