from typing import Any, Optional, Dict
from threading import Lock
from cache_policies import EvictionPolicy, LRUPolicy

class Cache:
    """
    A thread-safe in-memory cache with configurable capacity and eviction policy.
    """
    
    def __init__(self, capacity: int, policy: Optional[EvictionPolicy] = None):
        """
        Initialize the cache.
        
        Args:
            capacity: Maximum number of items the cache can hold
            policy: Eviction policy to use (defaults to LRU if None)
        """
        if capacity <= 0:
            raise ValueError("Capacity must be positive")
            
        self.capacity = capacity
        self.policy = policy if policy is not None else LRUPolicy()
        self.data: Dict[str, Any] = {}
        self.lock = Lock()
    
    def get(self, key: str) -> Optional[Any]:
        """
        Retrieve an item from the cache.
        
        Args:
            key: The key to look up
            
        Returns:
            The value if found, None otherwise
        """
        with self.lock:
            if key in self.data:
                self.policy.update(key)
                return self.data[key]
            return None
    
    def put(self, key: str, value: Any) -> None:
        """
        Add or update an item in the cache.
        
        Args:
            key: The key to store
            value: The value to store
        """
        with self.lock:

            if key not in self.data and  len(self.data)>=self.capacity:
                evict_key = self.policy.evict()
                if evict_key in self.data:
                    del self.data[evict_key]
            self.data[key] = value
            self.policy.update(key)

        # with self.lock:
        #     if key in self.data:
        #         self.data[key] = value
        #         self.policy.update(key)
        #     else:
        #         if len(self.data) >= self.capacity:
        #             evict_key = self.policy.evict()
        #             if evict_key is not None:
        #                 del self.data[evict_key]
        #
        #         self.data[key] = value
        #         self.policy.update(key)
    
    def remove(self, key: str) -> None:
        """
        Remove an item from the cache.
        
        Args:
            key: The key to remove
        """
        with self.lock:
            if key in self.data:
                del self.data[key]
                self.policy.remove(key)
    
    def clear(self) -> None:
        """Clear all items from the cache."""
        with self.lock:
            self.data.clear()
            self.policy = type(self.policy)()  # Reset policy
    
    def size(self) -> int:
        """Return the current number of items in the cache."""
        with self.lock:
            return len(self.data)
    
    def is_full(self) -> bool:
        """Check if the cache is at capacity."""
        with self.lock:
            return len(self.data) >= self.capacity