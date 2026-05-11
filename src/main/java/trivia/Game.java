package trivia;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGame {
    private static final int BOARD_SIZE = 12;
    private static final int WINNING_PURSE = 6;
    private static final int INITIAL_QUESTIONS = 50;

    private final List<Player> players = new ArrayList<>();
    private final QuestionDeck deck = new QuestionDeck(INITIAL_QUESTIONS);
    
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    public boolean add(String playerName) {
        players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    // Renombrado de isPlayable para ser más descriptivo
    public boolean hasEnoughPlayers() {
        return players.size() >= 2;
    }

    public void roll(int roll) {
        Player player = players.get(currentPlayerIndex);
        System.out.println(player.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(player.getName() + " is getting out of the penalty box");
                movePlayerAndAskQuestion(player, roll);
            } else {
                System.out.println(player.getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            movePlayerAndAskQuestion(player, roll);
        }
    }

    // Método extraído para evitar duplicidad de lógica de movimiento
    private void movePlayerAndAskQuestion(Player player, int roll) {
        player.setPlace((player.getPlace() + roll) % BOARD_SIZE);
        System.out.println(player.getName() + "'s new location is " + (player.getPlace() + 1));
        System.out.println(deck.askQuestion(player.getPlace()));
    }

    public boolean handleCorrectAnswer() {
        Player player = players.get(currentPlayerIndex);

        if (player.isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
            return nextTurn();
        }

        System.out.println("Answer was correct!!!!");
        player.addGoldCoin();
        System.out.println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");

        boolean winner = didPlayerWin(player);
        nextTurn();
        
        // El loop original espera 'true' para seguir jugando y 'false' si hay ganador
        return !winner; 
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");
        System.out.println(player.getName() + " was sent to the penalty box");
        player.setInPenaltyBox(true);

        nextTurn();
        return true;
    }

    private boolean nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return true;
    }

    // Lógica corregida: devuelve true si el jugador alcanzó las monedas necesarias
    private boolean didPlayerWin(Player player) {
        return player.getPurse() == WINNING_PURSE;
    }

    public int howManyPlayers() {
        return players.size();
    }
}