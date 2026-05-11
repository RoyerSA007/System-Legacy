from collections import deque

class QuestionDeck:
    def __init__(self, number_of_questions: int):
        # Usamos deque para simular LinkedList y permitir popleft() eficiente
        self._pop_questions = deque()
        self._science_questions = deque()
        self._sports_questions = deque()
        self._rock_questions = deque()

        for i in range(number_of_questions):
            self._pop_questions.append(f"Pop Question {i}")
            self._science_questions.append(f"Science Question {i}")
            self._sports_questions.append(f"Sports Question {i}")
            self._rock_questions.append(f"Rock Question {i}")

    def get_category(self, place: int) -> str:
        # Lógica de categorías basada en el módulo de la posición
        remainder = place % 4
        if remainder == 0:
            return "Pop"
        if remainder == 1:
            return "Science"
        if remainder == 2:
            return "Sports"
        return "Rock"

    def ask_question(self, place: int) -> str:
        category = self.get_category(place)
        print(f"The category is {category}")

        # Simulación del switch de Java usando un diccionario o if/elif
        if category == "Pop":
            return self._pop_questions.popleft()
        elif category == "Science":
            return self._science_questions.popleft()
        elif category == "Sports":
            return self._sports_questions.popleft()
        else:
            return self._rock_questions.popleft()