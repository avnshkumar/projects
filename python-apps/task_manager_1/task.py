import uuid
class Task:
    def __init__(self, user, title, priority):
        self.id = uuid.uuid4()
        self.user = user
        self.title = title
        self.priority = priority
        self.is_completed = False
