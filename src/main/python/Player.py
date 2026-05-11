class Player:
    def __init__(self, name: str):
        self._name = name
        self._place = 0  # Usamos 0-11 para facilitar el uso de módulos
        self._purse = 0
        self._in_penalty_box = False

    # Getters estilo Python (propiedades)
    def get_name(self):
        return self._name

    def get_place(self):
        return self._place

    def set_place(self, place: int):
        self._place = place

    def get_purse(self):
        return self._purse

    def add_gold_coin(self):
        self._purse += 1

    def is_in_penalty_box(self):
        return self._in_penalty_box

    def set_in_penalty_box(self, in_penalty_box: bool):
        self._in_penalty_box = in_penalty_box