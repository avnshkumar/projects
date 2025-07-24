from abc import ABC, abstractmethod

def SocketInterface(ABC):

    @abstractmethod
    def listen(self, host: str, port: int):
        """Start listening for incoming connections on the specified host and port."""
        pass
    @abstractmethod
    def send(self, message: str):
        """Send a message to the connected socket."""
        pass


