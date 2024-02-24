package models;

public class Machine extends Item{
    protected String condition;

    public Machine(int farmerId, String name, String description, double price, int quantityAvailable, String condition){
        super(farmerId, name, description, price, quantityAvailable);
        this.condition = condition;
    }

    public String getCondition(){
        return condition;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }
}
