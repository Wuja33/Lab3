package tools;

import models.Food;
import models.Order;
import models.Payment;

import java.util.ArrayList;
import java.util.List;

public class Data {
    static List<Food> menu;
    static List<Order> orders;
    static List<Payment> payments;
    public static String menuPath;
    public static String ordersPath;
    public static String paymentsPath;


    public static void firstImport(String menuPath2, String ordersPath2, String paymentsPath2)
    {
        menuPath = menuPath2;
        ordersPath = ordersPath2;
        paymentsPath = paymentsPath2;
        importAll();
    }
    public static void importAll()
    {
        importMenu();
        importOrder();
        importPayments();
    }

    public static void importMenu()
    {
        menu = new ArrayList<>();
        List<List<String>> lines = Import.createListofParametersFromFile(menuPath,";",true);

        for (List<String> line :
                lines) {
            menu.add(new Food(Integer.parseInt(line.get(0)),line.get(1),Float.parseFloat(line.get(2))));
        }
    }

    public static void importOrder()
    {
        orders = new ArrayList<>();
        List<List<String>> lines = Import.createListofParametersFromFile(ordersPath,";",false);

        for (List<String> line :
                lines) {
            List<String> listHelp= List.of(line.get(1).split(","));
            List<Food> foodList = new ArrayList<>();

            for (String x :
                    listHelp) {
                int index = 0;
                for (Food food : Data.menu) {
                    if (food.getId() == Integer.valueOf(x)) {
                        index = Data.menu.indexOf(food);
                        break;
                    }
                }
                foodList.add(new Food(Integer.valueOf(x),Data.menu.get(index).getDescription(),Data.menu.get(index).getPrice())); //string list -> Integer list
            }
            orders.add(new Order(Integer.parseInt(line.get(0)),foodList,Integer.parseInt(line.get(2)),Float.parseFloat(line.get(3))));
        }
    }

    public static void importPayments()
    {
        payments = new ArrayList<>();
        List<List<String>> lines = Import.createListofParametersFromFile(paymentsPath,";",false);
        int index = 0;
        boolean error=false;
        for (List<String> line :
                lines) {
            for (Order order :
                    orders) {
                if (order.getId() == Integer.valueOf(line.get(0))) //czy istnieje zamówienie o takim ID
                    index = orders.indexOf(order);
            }

            payments.add(new Payment(orders.get(index),line.get(1),Integer.valueOf(line.get(2))));
        }
    }

}
