from abc import abstractmethod, ABC
from comment import Comment


class Commentable(ABC):

    @abstractmethod
    def add_comment(self, comment: Comment):
        pass
    @abstractmethod
    def remove_comment(self, comment_id: str):
        pass
    @abstractmethod
    def get_comments(self):
        pass

