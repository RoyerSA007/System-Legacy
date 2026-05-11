class Game:
    BOARD_SIZE = 12
    WINNING_PURSE = 6
    INITIAL_QUESTIONS = 50

    def __init__(self):
        self.players = []
        # Asumiendo que QuestionDeck y Player existen en tu proyecto Python
        self.deck = QuestionDeck(self.INITIAL_QUESTIONS)
        self.current_player_index = 0
        self.is_getting_out_of_penalty_box = False

    def add(self, player_name):
        # Asumiendo que la clase Player se instancia así
        self.players.append(Player(player_name))
        print(f"{player_name} was added")
        print(f"They are player number {len(self.players)}")
        return True

    def has_enough_players(self):
        return len(self.players) >= 2

    def roll(self, roll_value):
        player = self.players[self.current_player_index]
        print(f"{player.get_name()} is the current player")
        print(f"They have rolled a {roll_value}")

        if player.is_in_penalty_box():
            if roll_value % 2 != 0:
                self.is_getting_out_of_penalty_box = True
                print(f"{player.get_name()} is getting out of the penalty box")
                self._move_player_and_ask_question(player, roll_value)
            else:
                print(f"{player.get_name()} is not getting out of the penalty box")
                self.is_getting_out_of_penalty_box = False
        else:
            self._move_player_and_ask_question(player, roll_value)

    def _move_player_and_ask_question(self, player, roll_value):
        # El guion bajo al inicio indica que es un método "privado" por convención
        new_place = (player.get_place() + roll_value) % self.BOARD_SIZE
        player.set_place(new_place)
        
        print(f"{player.get_name()}'s new location is {player.get_place() + 1}")
        print(self.deck.ask_question(player.get_place()))

    def handle_correct_answer(self):
        player = self.players[self.current_player_index]

        if player.is_in_penalty_box() and not self.is_getting_out_of_penalty_box:
            return self._next_turn()

        print("Answer was correct!!!!")
        player.add_gold_coin()
        print(f"{player.get_name()} now has {player.get_purse()} Gold Coins.")

        winner = self._did_player_win(player)
        self._next_turn()

        # Devuelve True para seguir jugando, False si hay un ganador
        return not winner

    def wrong_answer(self):
        player = self.players[self.current_player_index]
        print("Question was incorrectly answered")
        print(f"{player.get_name()} was sent to the penalty box")
        player.set_in_penalty_box(True)

        self._next_turn()
        return True

    def _next_turn(self):
        self.current_player_index = (self.current_player_index + 1) % len(self.players)
        return True

    def _did_player_win(self, player):
        return player.get_purse() == self.WINNING_PURSE

    def how_many_players(self):
        return len(self.players)