from abc import ABC, abstractmethod
from collections import OrderedDict
from typing import Any, Dict, Optional
import heapq
import time

class EvictionPolicy(ABC):
    """Abstract base class for cache eviction policies."""
    
    @abstractmethod
    def update(self, key: str) -> None:
        """Update the policy state when a key is accessed."""
        pass
    
    @abstractmethod
    def evict(self) -> Optional[str]:
        """Return the key that should be evicted."""
        pass
    
    @abstractmethod
    def remove(self, key: str) -> None:
        """Remove a key from the policy tracking."""
        pass

class LRUPolicy(EvictionPolicy):
    """Least Recently Used eviction policy."""
    
    def __init__(self):
        self.order = OrderedDict()
    
    def update(self, key: str) -> None:
        if key in self.order:
            self.order.move_to_end(key, last=False)
        else:
            self.order[key] = None
    
    def evict(self) -> Optional[str]:
        if not self.order:
            return None
        return self.order.popitem()[0]
    
    def remove(self, key: str) -> None:
        if key in self.order:
            del self.order[key]

class PriorityPolicy(EvictionPolicy):
    """Priority-based eviction policy using a min heap."""
    
    def __init__(self):
        self.heap = []  # (priority, timestamp, key)
        self.key_map: Dict[str, float] = {}  # key -> priority mapping
        
    def set_priority(self, key: str, priority: float) -> None:
        """Set or update priority for a key."""
        self.key_map[key] = priority
        timestamp = time.time()
        heapq.heappush(self.heap, (priority, timestamp, key))
    
    def update(self, key: str) -> None:
        if key not in self.key_map:
            self.set_priority(key, 0.0)  # Default priority
    
    def evict(self) -> Optional[str]:
        while self.heap:
            _, _, key = heapq.heappop(self.heap)
            if key in self.key_map:  # Check if key is still valid
                del self.key_map[key]
                return key
        return None
    
    def remove(self, key: str) -> None:
        if key in self.key_map:
            del self.key_map[key]
            # Note: Heap will be cleaned up lazily during eviction 