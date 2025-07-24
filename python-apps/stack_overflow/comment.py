class Comment:
    def __init__(self, question_id, author_id,  content ):
        self.question_id:str = question_id
        self.author_id:str = author_id
        self.content:str = content