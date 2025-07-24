import uuid
class User:
    def __init__(self, name):
        self.id = uuid.uuid4()
        self.name = name