from abc import abstractmethod, ABC
from vote import VoteValue


class Votable(ABC):

    @abstractmethod
    def vote(self, user_id: str, value: VoteValue):
        pass

    @abstractmethod
    def vote_value(self):
        pass