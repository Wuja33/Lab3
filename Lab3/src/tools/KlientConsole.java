package tools;

import models.Food;
import models.Order;
import models.Payment;

import java.util.*;

public abstract class KlientConsole {
    static List<Order> listOrdersClient = new ArrayList<>();


    public static void start(String menuPath, String ordersPath, String paymentsPath)
    {
        Data.firstImport(menuPath,ordersPath,paymentsPath);
        menu();
    }
    
    private static void showMenu(int choose)
    {
        switch (choose)
        {
            case 0:
                System.out.println(
                        "KLIENT\n"+
                            "1. Pokaż menu \n" +
                            "2. Złóż zamówienie\n" +
                            "3. Pokaż złożone zamówienia\n"+
                            "4. Odśwież\n"+
                            "0. Zamknij aplikację"+
                            "\nWybierz opcje: "); break;
            case 1:
                for (Food food:
                     Data.menu) {
                    System.out.println(food.toString());
                }
                System.out.println("\n0. Wróć do panelu startowego");
                break;
            case 2:
                for (Food food:
                        Data.menu) {
                    System.out.println(food.toString());
                }
                System.out.println("\n0. Powrót");
                break;
            case 3:
                for (Order order :
                        listOrdersClient) {
                    System.out.println(order.toString());
                }
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
                    List<Integer> listFoods = new ArrayList<>(); //możliwe opcje
                    HashMap<Integer, Integer> listOrderedFoods = new HashMap<>(); //lista zamówionych rzeczy

                    showMenu(2);
                    for (Food food :
                            Data.menu) {
                        listFoods.add(food.getId()); //szukanie wszystkich możliwych opcji
                    }

                    int chooseFood;
                    boolean orderStarted = false;
                    while (true) {

                        if (orderStarted) {
                            System.out.println("Wybrane pozycje: ");
                            listOrderedFoods.forEach((key, value) ->
                            {
                                System.out.println(key + "  x" + value); //wyświetlanie aktualnego zamówienia
                            });

                            System.out.print("Czy chcesz zakończyć zamówienie (T - TAK, N - NIE): ");
                            if (menuChoiceChar(List.of('T', 'N')) == 'T') {
                                List<Food> help = new ArrayList<>();
                                var ref = new Object() {
                                    Float price = 0.0f;
                                };
                                listOrderedFoods.forEach((key, value) ->
                                {
                                    int index = 0;
                                    for (Food food : Data.menu) {
                                        if (food.getId() == key) {
                                            index = Data.menu.indexOf(food);
                                            break;
                                        }
                                    }
                                    for (int i = 0; i < value; i++) {
                                        help.add(new Food(key, Data.menu.get(index).getDescription(),Data.menu.get(index).getPrice())); //tworzenie listy potraw z zamówienia
                                        ref.price += Data.menu.get(index).getPrice();
                                    }
                                });
                                createOrder(help, ref.price);
                                System.out.println("Zamówiono! ");
                                break;
                            }
                        }

                        System.out.println("\nWybierz pozycję, którą chcesz zamówić: ");

                        chooseFood = menuChoiceInt(listFoods);
                        if (chooseFood != 0) {
                            System.out.println("Podaj ilość (max. 9): ");
                            listOrderedFoods.put(chooseFood, menuChoiceInt(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
                            System.out.println("Dodano do zamówienia! ");
                            orderStarted = true;
                        } else break;
                    }
                    break;
                case 3:
                    if (listOrdersClient.size()!=0) {
                        showMenu(3);
                        List<Integer> listOrdersToCollect = new ArrayList<>();
                        boolean oneIteration = false;
                        for (Order order : listOrdersClient) {
                            if (order.getStatus() == 2) {
                                if (!oneIteration)
                                    System.out.print("Wybierz zamówienie, które chcesz odebrać: ");
                                listOrdersToCollect.add(order.getId()); //szukanie zamówień do odebrania (robienie listy możliwych opcji
                            }
                        }
                        System.out.println("\n0. Wróć do panelu startowego");
                        listOrdersToCollect.add(0); //dodanie możliwości wybrania opcji 0
                        int choose = menuChoiceInt(listOrdersToCollect);
                        if (choose == 0)
                            continue;
                        else {
                            Data.importAll();
                            boolean one = false;
                            for (Order order: listOrdersClient)
                            {
                                if (order.getId()==choose)
                                    order.setStatus(3);
                            }
                            for (Order order:
                                 Data.orders) {
                                if (order.getId()==choose) {
                                    order.setStatus(3);
                                }
                                if (one)
                                    Import.createFileOrders(order,Data.ordersPath);
                                else
                                    Import.appendNewElement(order,Data.ordersPath);
                                one = true;
                            }
                            System.out.print("\n Wybierz metodę płatności\n" +
                                    "1. Płatność przelewem\n" +
                                    "2. Płatność gotówką\nPodaj liczbę:");
                            int system = menuChoiceInt(List.of(1, 2));
                            boolean firstIteration = false;
                            for (Payment payment :
                                    Data.payments) {
                                if (payment.getOrder().getId() == choose) {
                                    payment.getOrder().setStatus(3);
                                    payment.setStatus(1);
                                    switch (system) {
                                        case 1:
                                            payment.setSystem("PRZELEW");
                                            break;
                                        case 2:
                                            payment.setSystem("GOTÓWKĄ");
                                            break;
                                    }
                                    break;
                                }
                                if (firstIteration)
                                    Import.appendNewElement2(new Payment(payment.getOrder(),payment.getSystem(),1),Data.paymentsPath);
                                else
                                    Import.appendNewElement(new Payment(payment.getOrder(),payment.getSystem(),1),Data.paymentsPath);
                                firstIteration = true;
                            }
                        }
                    }
                    else System.out.println("BRAK ZŁOŻONYCH ZAMÓWIEŃ");
                    break;
                case 4:
                    Data.importAll();
                    for (Order order :
                            Data.orders) {
                        for (Order orderClient :
                                listOrdersClient) {
                            if (order.getId() == orderClient.getId())
                                orderClient.setStatus(order.getStatus()); //zmiana statusu przy odświeżeniu
                        }
                    }
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
    private static int menuChoiceInt(List<Integer> listOfChoices, int choose) //funkcja do interakcji z użytkownikiem
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
            } finally {
                System.out.println("Podano złą wartość! Podaj ponownie.");
            }
        }
        return chooseHelp;
    }
    private static char menuChoiceChar(List<Character> listOfChoices) //funkcja do interakcji z użytkownikiem
    {
        Scanner scan = new Scanner(System.in);
        char chooseHelp;
        while (true) {
            try {
                chooseHelp = scan.next().charAt(0);
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
    private static void createOrder(List<Food> listOfOrderedFood,Float price)
    {
        Order order;
        Data.importOrder();
        if (Data.orders.size()>0)
            order = new Order(Data.orders.get(Data.orders.size()-1).getId()+1,listOfOrderedFood ,0,price); //dodaj zamówienie o id+1, co ostanie z listy zamówień
        else
            order = new Order(1,listOfOrderedFood ,0,price); //dodaj pierwsze zamówienie
        Import.appendNewElement(order,Data.ordersPath);
        listOrdersClient.add(order);
        Data.orders.add(order);
    }
}