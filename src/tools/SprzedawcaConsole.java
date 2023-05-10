package tools;

import models.Food;
import models.Order;
import models.Payment;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SprzedawcaConsole {

    public static void start(String menuPath, String ordersPath, String paymentsPath) {
        Data.firstImport(menuPath, ordersPath, paymentsPath);
        menu();
    }

    private static void showMenu(int choose) {
        switch (choose) {
            case 0:
                System.out.print(
                        "SPRZEDAWCA\n"+
                        "1. Pokaż menu \n" +
                                "2. Oczekujące zamówienia\n" +
                                "3. Wszystkie płatności\n" +
                                "4. Odśwież\n"+
                                "0. Zamknij aplikację" +
                                "\nWybierz opcje: ");
                break;
            case 1:
                for (Food food :
                        Data.menu) {
                    System.out.println(food.toString());
                }
                System.out.println("\n0. Wróć do panelu startowego");
                break;
            case 2:
                for (Order order :
                        Data.orders) {
                    if (order.getStatus() == 1)
                        System.out.println(order.toString());
                }
                System.out.println("\n0. Powrót");
                System.out.print("\nWybierz zamówienie, które chcesz wydać klientowi: ");

                break;
            case 3:

                break;
            case 4:
                System.out.println("Odświeżono!");
                break;
        }
    }

    private static void menu() //funkcja do obsługi menu
    {
        boolean shouldBreak = false;
        do {
            showMenu(0);
            switch (menuChoiceInt(List.of(1, 2, 3, 4, 0))) {
                case 1:
                    showMenu(1);
                    menuChoiceInt(List.of(0));
                    //POWRÓT
                    break;
                case 2:
                    List<Order> listOrders = new ArrayList<>();
                    List<Integer> idOrders = new ArrayList<>();
                    int chooseOrder;
                    for (Order order :
                            Data.orders) {
                        if (order.getStatus() == 1) {
                            listOrders.add(order);
                            idOrders.add(order.getId());
                        }
                    }
                    idOrders.add(0); //dodanie możliwości wyjścia
                    while (true) {
                        showMenu(2);
                        chooseOrder = menuChoiceInt(idOrders);
                        Data.importAll(); //odświeżenie stanu
                        boolean firstIteration = false;
                        if (chooseOrder != 0) {
                            listOrders.remove(idOrders.indexOf(chooseOrder));
                            idOrders.remove(idOrders.indexOf(chooseOrder));
                            for (Order order :
                                    Data.orders) {
                                if (order.getId() == chooseOrder) {
                                    order.setStatus(2); //zmiana statusu
                                    Import.appendNewElement(new Payment(order,"OCZEKIWANIE",0),Data.paymentsPath);
                                }
                                if (!firstIteration)
                                    Import.createFileOrders(order,Data.ordersPath); //nadpisywanie nowego pliku
                                else
                                    Import.appendNewElement(order,Data.ordersPath);
                                firstIteration = true;
                            }
                            System.out.println("Zamówienie oczekuje na odbiór oraz na zrealizowanie płatności!\n");
                        } else break;
                    }
                    break;
                case 3:
                    showMenu(3);
                    for (Payment payment :
                            Data.payments) {
                        System.out.println(payment.toString());
                    }
                    break;
                case 4:
                    Data.importAll();
                    showMenu(4);
                    break;
                case 0:
                    shouldBreak = true;
                    break;
            }
        } while (!shouldBreak);
    }

    private static int menuChoiceInt(List<Integer> listOfChoices) //funkcja do interakcji z użytkownikiem
    {
        Scanner scan = new Scanner(System.in);
        int chooseHelp;
        while (true) {
            try {
                chooseHelp = scan.nextInt();
                if (listOfChoices.contains(chooseHelp))
                    break;
            } catch (InputMismatchException e) {
                scan.nextLine(); //czyszczenie
                System.out.println("Podano złą wartość! Podaj ponownie.");
            }
            System.out.println("Podano złą wartość! Podaj ponownie.");
        }
        return chooseHelp;
    }
}


