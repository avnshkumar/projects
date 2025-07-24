import socket
from time import sleep



def main():
    host = "127.0.0.1"
    port = "10003"
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((host, port))
        s.sendall(b'This is socket C')
        print("Socket C is waiting for next message")
        while True:
            data = s.recv(1024)
            if not data:
                print("Socket C is Sleeping for .5secs")
                sleep(500)
            else:
                print(f"Message received on socket C: {data.decode()}")

if __name__=="__main__":
    socket_c_receiver()
