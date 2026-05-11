from abc import ABC, abstractmethod

class IGame(ABC):

    @abstractmethod
    def add(self, player_name: str) -> bool:
        """Agrega un jugador al juego."""
        pass

    @abstractmethod
    def roll(self, roll_value: int) -> None:
        """Simula el lanzamiento de un dado."""
        pass

    @abstractmethod
    def handle_correct_answer(self) -> bool:
        """Maneja la lógica cuando una respuesta es correcta."""
        pass

    @abstractmethod
    def wrong_answer(self) -> bool:
        """Maneja la lógica cuando una respuesta es incorrecta."""
        pass