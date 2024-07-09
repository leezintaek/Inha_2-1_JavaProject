package userdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    //객체를 저장하기 위함
    private String id;
    private String password;
    private String name;
    private String job;
    private List<Node> earnings;
    private List<Node> expenses;
    private int assets; //총 자산

    public User(String id, String password) {
        this.id = id;
        this.password = password;
        this.earnings = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.assets = 0;
    }

    public User(String id, String password, String name, String job) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.job = job;
        this.earnings = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.assets = 0;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public List<Node> getEarnings() {
        return earnings;
    }

    public List<Node> getExpenses() {
        return expenses;
    }

    public int getAssets() {
        return assets;
    }

    public void setEarnings(List<Node> earnings) {
        this.earnings = earnings;
    }

    public void setExpenses(List<Node> expenses) {
        this.expenses = expenses;
    }

    public void setAssets(int assets) {
        this.assets = assets;
    }

    public List<Node> getEarning() {
        return earnings;
    }

    public List<Node> getExpense() {
        return expenses;
    }

    public void addEarning(Node node) {
        earnings.add(node);
        assets += node.getAmount();
    }

    public void addExpense(Node node) {
        expenses.add(node);
        assets -= node.getAmount();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", assets=" + assets +
                '}';
    }
}
