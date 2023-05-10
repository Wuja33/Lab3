package mains;

import tools.KlientConsole;
import tools.SprzedawcaConsole;

public class SprzedawcaApp {
    public static void main(String[] args) {
        SprzedawcaConsole.start("data/Menu.txt", "data/Orders.txt", "data/Payments.txt");
    }
}
