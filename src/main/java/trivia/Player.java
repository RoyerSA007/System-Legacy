package trivia;

public class Player {
    private final String name;
    private int place = 0; // Usaremos 0-11 para facilitar el uso de módulos
    private int purse = 0;
    private boolean inPenaltyBox = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    
    public int getPlace() { return place; }
    public void setPlace(int place) { this.place = place; }

    public int getPurse() { return purse; }
    public void addGoldCoin() { this.purse++; }

    public boolean isInPenaltyBox() { return inPenaltyBox; }
    public void setInPenaltyBox(boolean inPenaltyBox) { this.inPenaltyBox = inPenaltyBox; }
}