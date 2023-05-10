package models;

public class Payment {
    private Order order;
    private String system;
    private int status;
    private double price;

    public Payment(Order order, String system, int status)
    {
        this.order = order;
        this.system = system;
        this.status = status;
        this.price = order.getPrice();
    }

    public double getPrice() {
        return price;
    }

    public Order getOrder() {
        return order;
    }

    public String getSystem() {
        return system;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString()
    {
        String out = "ID Order: "+order.getId()+"\nSystem: "+system+"\nStatus: ";
        switch (status)
        {
            case 0:
                out+="Oczekiwanie na płatność";
                break;
            case 1:
                out+="Płatność zakończona";
                break;
        }
        return out+"\n";
    }
}
