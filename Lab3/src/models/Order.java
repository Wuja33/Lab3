package models;

import java.util.*;

public class Order {
    private int id;
    private int status;
    List<Food> listOfOrderedFood;
    private Float price;

    public Order(int id, List<Food> list,int status, Float price)
    {
        this.status = status;
        this.id = id;
        this.listOfOrderedFood = list;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public List<Food> getListOfOrderedFood() {
        return listOfOrderedFood;
    }

    public int getStatus() {
        return status;
    }

    public Float getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListOfOrderedFood(List<Food> listOfOrderedFood) {
        this.listOfOrderedFood = listOfOrderedFood;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString()
    {
        String out = "ID: "+id+"\nZamówione pozycje: "+listToPrint()+"\nKwota: "+String.format("%.2f",price)+"\nStatus: ";

        switch (status)
        {
            case 0: out+="Przekazane do wykonania"; break;
            case 1: out+=" Przekazane do kasy"; break;
            case 2: out+="Oczekuje na odbiór"; break;
            case 3: out+="Zamówienie odebrane"; break;
        }

        return out+"\n";
    }
    public String listToString()
    {
        String help = "";
        for (int i = 0; i < listOfOrderedFood.size(); i++) {
            if (i==listOfOrderedFood.size()-1)
                help+=listOfOrderedFood.get(i).getId();
            else
                help+=listOfOrderedFood.get(i).getId()+",";
        }
        return help;
    }
    public String listToPrint()
    {
        String print = "";
        List<Integer> helpList = new ArrayList<>();
        for (Food food: listOfOrderedFood) {
            helpList.add(food.getId());
        }
        Set<Integer> help = new HashSet<>(helpList);
        for (Integer id :
                help) {
            print+=id +" - ilość: "+ Collections.frequency(helpList,id)+"\n";
        }

        return print;
    }
}