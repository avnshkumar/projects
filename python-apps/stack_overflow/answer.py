from time import time
from typing import List
from votable import Votable
from commentable import Commentable
from comment import Comment
from vote import Vote, VoteValue

class Answer(Votable, Commentable):
    def __init__(self, author_id: str, question_id: str, content: str):
        self.author_id = author_id
        self.question_id = question_id
        self.content = content
        self.is_accepted = False
        self.created_at = time()
        self.updated_at = time()
        self.comments: List[Comment] = []
        self.votes: List[Vote] = []

    def vote(self, user_id: str, value: VoteValue):
        # check if user id has already voted
        if user_id == self.author_id:
            raise ValueError("You can't vote yourself")
        if user_id in list(map(lambda vote: vote.user_id, self.votes)):
            raise ValueError("User already voted")
        self.votes.append(Vote(user_id, value))
        if(len())

