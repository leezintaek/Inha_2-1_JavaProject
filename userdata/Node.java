package userdata;

import java.io.Serializable;
import java.time.LocalDate;

public class Node implements Serializable {
    private String name;
    private int amount;
    private LocalDate date;
    private boolean isFixed;

    public Node(String name, int amount, LocalDate date, boolean isFixed) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.isFixed = isFixed;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }

    public boolean isFixed() {
        return isFixed;
    }
    public void setFixed(boolean isFixed){
        this.isFixed = isFixed;
    }

    @Override
    public String toString() {
        return name + " | " + amount + " | " + date + " | " + (isFixed ? "Fixed" : "Variable");
    }
}