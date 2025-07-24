import time

from basic_socket_connection.SocketInterface import SocketInterface
import socket
import threading


class BasicSocket(SocketInterface):

    def __init__(self, name:str = "A", host: str = "127.0.0.1", port: int = "10000"):
        self.name = name
        self.host = host
        self.port = port
        self.socket = None
        self.listening_thread = threading.Thread(target=self.listen)
        self.sender_thread = threading.Thread(target=self.send)



    def listen(self):
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.bind((self.host, self.port))
        print(f"Listening on {self.host}:{self.port}")
        self.socket.listen(1)
        conn, addr = self.socket.accept()
        count = 0
        while True:
            if count > 10:
                print("No data received for a while, closing connection.")
                conn.close()
                break
            print(f"Connection established with {addr}")
            data = conn.recv(1024)
            if not data:
                print("No data received, sleeping for 0.5 seconds...")
                time.sleep(0.5)
                count += 1
                continue
            print(f"Message received: {data.decode()}")

    def send(self, message: str):
        if not self.socket:
            raise RuntimeError("Socket is not initialized. Call listen() first.")
        conn, addr = self.socket.accept()
        print(f"Connection established with {addr}")
        conn.sendall(message.encode())
        conn.close()