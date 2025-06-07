package implementation.model;

public class Player {
    private int number;
    private String name;

    public Player(int number, String name){
        this.number = number;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNumber(){
        return number;
    }
}
