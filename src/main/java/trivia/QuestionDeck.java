package trivia;

import java.util.LinkedList;

public class QuestionDeck {
    private final LinkedList<String> popQuestions = new LinkedList<>();
    private final LinkedList<String> scienceQuestions = new LinkedList<>();
    private final LinkedList<String> sportsQuestions = new LinkedList<>();
    private final LinkedList<String> rockQuestions = new LinkedList<>();

    public QuestionDeck(int numberOfQuestions) {
        for (int i = 0; i < numberOfQuestions; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast("Science Question " + i);
            sportsQuestions.addLast("Sports Question " + i);
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public String getCategory(int place) {
        if (place % 4 == 0) return "Pop";
        if (place % 4 == 1) return "Science";
        if (place % 4 == 2) return "Sports";
        return "Rock";
    }

    public String askQuestion(int place) {
        String category = getCategory(place);
        System.out.println("The category is " + category);
        
        switch (category) {
            case "Pop": return popQuestions.removeFirst();
            case "Science": return scienceQuestions.removeFirst();
            case "Sports": return sportsQuestions.removeFirst();
            default: return rockQuestions.removeFirst();
        }
    }
}