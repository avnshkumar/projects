from enum import Enum
class Vote:
    def __init__(self, user_id, value):
        self.user_id:str = user_id
        self.value:VoteValue = value


class VoteValue(str,Enum):
    UPVOTE = "UPVOTE"
    DOWNVOTE = "DOWNVOTE"
