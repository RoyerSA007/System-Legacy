package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class Game implements IGame {
    """ SMELL 1: ARRAYS PARALELOS
    Los datos del jugador (nombre, posición, monedas, penalización) 
    están dispersos en 4 estructuras distintas que dependen de un 
    índice compartido. Debería existir una clase 'Player'. """
   ArrayList players = new ArrayList();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   LinkedList popQuestions = new LinkedList();
   LinkedList scienceQuestions = new LinkedList();
   LinkedList sportsQuestions = new LinkedList();
   LinkedList rockQuestions = new LinkedList();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public Game() {
      """ SMELL 2: NÚMERO MÁGICO (50)
      El número 50 está "hardcodeado"
      Debería ser una constante configurativa"""
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   """SMELL 3: NOMBRE ENGAÑOSO / LÓGICA INVERSA
   isPlayable() sugiere una comprobación de estado, pero según las 
   reglas de este juego suele usarse para ver si hay jugadores 
   suficientes (mínimo 2)"""
   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      places[howManyPlayers()] = 1;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;
      players.add(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   """ SMELL 4: MÉTODO LARGO Y RESPONSABILIDAD MÚLTIPLE
   roll() hace de todo: mueve al jugador, calcula si sale de la cárcel, 
   imprime en consola, decide la categoría y lanza la pregunta
   Viola el Principio de Responsabilidad Única"""
   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            
            // SMELL 2b: NÚMERO MÁGICO (12)
            // El tamaño del tablero (12) aparece aquí y en otras partes sin explicación.
            if (places[currentPlayer] > 12) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > 12) places[currentPlayer] = places[currentPlayer] - 12;

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places[currentPlayer]);
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void askQuestion() {
      if (currentCategory() == "Pop")
         System.out.println(popQuestions.removeFirst());
      if (currentCategory() == "Science")
         System.out.println(scienceQuestions.removeFirst());
      if (currentCategory() == "Sports")
         System.out.println(sportsQuestions.removeFirst());
      if (currentCategory() == "Rock")
         System.out.println(rockQuestions.removeFirst());
   }


   private String currentCategory() {
      if (places[currentPlayer] - 1 == 0) return "Pop";
      if (places[currentPlayer] - 1 == 4) return "Pop";
      if (places[currentPlayer] - 1 == 8) return "Pop";
      if (places[currentPlayer] - 1 == 1) return "Science";
      if (places[currentPlayer] - 1 == 5) return "Science";
      if (places[currentPlayer] - 1 == 9) return "Science";
      if (places[currentPlayer] - 1 == 2) return "Sports";
      if (places[currentPlayer] - 1 == 6) return "Sports";
      if (places[currentPlayer] - 1 == 10) return "Sports";
      return "Rock";
   }

   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + purses[currentPlayer]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }

   """ SMELL 5: NOMBRE CONFUSO Y LÓGICA BOLEANA EXTRAÑA
   didPlayerWin() devuelve 'false' si el jugador realmente gana  
   (llega a 6 monedas)"""
   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}