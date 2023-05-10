package mains;


import tools.KlientConsole;

import java.nio.file.Path;
/**
 * @author Dawid Szeląg
 * Kompilacja:
 * javac mains/KlientApp.java -encoding UTF8
 * javac mains/SprzedawcaApp.java -encoding UTF8
 * javac mains/KucharzApp.java -encoding UTF8
 *
 * Budowanie:
 * jar cfe ClientApp.jar mains.KlientApp mains/KlientApp.class *
 * jar cfe SprzedawcaApp.jar mains.SprzedawcaApp mains/SprzedawcaApp.class *
 * jar cfe KucharzApp.jar mains.KucharzApp mains/KucharzApp.class *
 *
 * Uruchamianie:
 * jar xf lab03_pop.jar
 * Po wypakowaniu jara, uruchomić plik 1+1+1.bat, uruchamia on 3 okna (dla Klienta, sprzedawcy i kucharza)
 */

public class KlientApp {
    public static void main(String[] args) {
        KlientConsole.start("data/Menu.txt", "data/Orders.txt", "data/Payments.txt");

    }
}