# Flexible In-Memory Cache

A thread-safe, flexible in-memory cache implementation with support for customizable capacity and eviction policies.

## Features

- **Configurable Capacity**: Set maximum items the cache can hold
- **Multiple Eviction Policies**:
  - LRU (Least Recently Used)
  - Priority-based eviction
- **Thread Safety**: All operations are thread-safe
- **Extensible**: Easy to add new eviction policies
- **Type Hints**: Full Python type annotations for better IDE support

## Usage

### Basic Usage with LRU Policy

```python
from cache import Cache

# Create a cache with capacity of 1000 items
cache = Cache(capacity=1000)

# Add items
cache.put("key1", "value1")
cache.put("key2", "value2")

# Get items
value = cache.get("key1")  # Returns "value1"

# Remove items
cache.remove("key1")
```

### Using Priority-Based Eviction

```python
from cache import Cache
from cache_policies import PriorityPolicy

# Create a priority-based cache
policy = PriorityPolicy()
cache = Cache(capacity=1000, policy=policy)

# Add items with priorities
cache.put("key1", "value1")
policy.set_priority("key1", 1.0)  # Lower priority

cache.put("key2", "value2")
policy.set_priority("key2", 2.0)  # Higher priority

# When cache is full, items with lower priority are evicted first
```

## Implementation Details

### Cache Class

The main `Cache` class provides the following methods:

- `__init__(capacity: int, policy: Optional[EvictionPolicy] = None)`: Initialize cache
- `get(key: str) -> Optional[Any]`: Retrieve an item
- `put(key: str, value: Any) -> None`: Add or update an item
- `remove(key: str) -> None`: Remove an item
- `clear() -> None`: Remove all items
- `size() -> int`: Get current number of items
- `is_full() -> bool`: Check if cache is at capacity

### Eviction Policies

#### LRU Policy
- Uses `OrderedDict` for O(1) access and updates
- Automatically tracks access order
- Evicts least recently used items when cache is full

#### Priority Policy
- Uses min heap for priority management
- Supports custom priority values
- Evicts lowest priority items first
- Includes timestamp to break priority ties

## Thread Safety

The implementation uses Python's threading.Lock to ensure thread safety:
- All cache operations are atomic
- Safe for concurrent access from multiple threads
- Prevents race conditions during eviction

## Extending

To add a new eviction policy:

1. Create a new class inheriting from `EvictionPolicy`
2. Implement the required methods:
   - `update(key: str) -> None`
   - `evict() -> Optional[str]`
   - `remove(key: str) -> None`

## Testing

Run the tests using:

```bash
python -m unittest test_cache.py
```

The test suite includes:
- Basic functionality tests
- LRU policy tests
- Priority policy tests
- Thread safety tests

## Distributed Cache Considerations

For distributed systems, consider the following extensions:

1. **Partitioning Strategy**:
   - Consistent hashing for key distribution
   - Virtual nodes for better balance

2. **Replication**:
   - Master-slave replication
   - Multi-master replication
   - Quorum-based consistency

3. **Consistency**:
   - Eventual consistency
   - Strong consistency options
   - Write-ahead logging

4. **Hot Key Handling**:
   - Key-based sharding
   - Read replicas
   - Local caching layers

5. **Failure Handling**:
   - Node failure detection
   - Automatic failover
   - Data rebalancing 