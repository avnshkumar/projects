from sortedcontainers import SortedDict, SortedList
from typing import List, Dict
from hashlib import md5

class Servers:
    def __init__(self, initial_servers:int = 4, virtual_replicas:int = 20, hash_function: callable = None):
        self.virtual_replicas = virtual_replicas
        self.hash_key_ to_virtual_replicas_map = SortedDict()
        self.virtual_hash_keys: SortedList[str] = SortedList()
        self.virtual_replicas_to_server_map: Dict[str, str] = {}
        self.hash_function = hash_function if hash_function else lambda x: md5(x.encode()).hexdigest()
        for i in range(initial_servers):
            server_name = f"server-{str(i)}"
            for j in range(self.virtual_replicas):
                replica_name = f"{server_name}#{str( chr(j + ord('a')) )}"
                hash_key = str(self.hash_function(replica_name))
                print(f"Replica: {replica_name}, Hash Key: {hash_key}, Server: {server_name}")
                self.hash_key_to_virtual_replicas_map[hash_key] = replica_name
                self.virtual_hash_keys.add(hash_key)
                self.virtual_replicas_to_server_map[hash_key] = server_name
    def get_server_for_key(self, key:str) -> str:
        if not self.virtual_hash_keys:
            raise ValueError("No servers available to handle the key.")
        hash_key = str(self.hash_function(key))
        index = self.virtual_hash_keys.bisect_left(hash_key)
        if index == len(self.virtual_hash_keys):
            index = 0
        return self.virtual_replicas_to_server_map[self.virtual_hash_keys[index]]

# test
if __name__ == "__main__":
    servers = Servers(initial_servers=5, virtual_replicas=50, hash_function=None)
    test_keys = [f"key-{i}" for i in range(10000)]
    print("Testing consistent hashing with 4 servers and 50 virtual replicas each.")
    print("Server info:")
    print("Hash key to virtual replicas map:")
    print (servers.hash_key_to_virtual_replicas_map)
    print("Virtual hash keys:")
    print(servers.virtual_hash_keys)
    print("Virtual replicas to server map:")
    print(servers.virtual_replicas_to_server_map)
    print("\nAssigning keys to servers:")
    # Assign keys to servers
    for key in test_keys:
        server = servers.get_server_for_key(key)
        print(f"Key: {key} is assigned to Server: {server}")