package mains;

import tools.KlientConsole;
import tools.KucharzConsole;

public class KucharzApp {
    public static void main(String[] args)
    {
        KucharzConsole.start("data/Menu.txt", "data/Orders.txt", "data/Payments.txt");
    }
}
