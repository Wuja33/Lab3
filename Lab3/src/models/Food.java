package models;


public class Food {
    private int id;
    private String description;
    private Float price;

    public Food(int id, String description, Float price)
    {
        this.id = id;
        this.description = description;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return id+". "+description+" - "+String.format("%.2f",price);
    }
}
