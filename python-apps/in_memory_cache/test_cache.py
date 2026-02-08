import unittest
from cache import Cache
from cache_policies import LRUPolicy, PriorityPolicy

class TestCache(unittest.TestCase):
    def test_lru_cache(self):
        # Test basic LRU functionality
        cache = Cache(capacity=2)
        
        # Add items
        cache.put("key1", "value1")
        cache.put("key2", "value2")
        self.assertEqual(cache.size(), 2)
        
        # Verify items
        self.assertEqual(cache.get("key1"), "value1")
        self.assertEqual(cache.get("key2"), "value2")
        
        # Add third item, should evict key1 (least recently used)
        cache.put("key3", "value3")
        self.assertIsNone(cache.get("key1"))
        self.assertEqual(cache.get("key2"), "value2")
        self.assertEqual(cache.get("key3"), "value3")
    
    def test_priority_cache(self):
        # Test priority-based eviction
        policy = PriorityPolicy()
        cache = Cache(capacity=2, policy=policy)
        
        # Add items with priorities
        cache.put("key1", "value1")
        policy.set_priority("key1", 1.0)
        
        cache.put("key2", "value2")
        policy.set_priority("key2", 2.0)
        
        # Add third item, should evict key1 (lowest priority)
        cache.put("key3", "value3")
        policy.set_priority("key3", 3.0)
        
        self.assertIsNone(cache.get("key1"))
        self.assertEqual(cache.get("key2"), "value2")
        self.assertEqual(cache.get("key3"), "value3")
    
    def test_thread_safety(self):
        import threading
        import time
        
        cache = Cache(capacity=100)
        errors = []
        
        def worker(start, count):
            try:
                for i in range(start, start + count):
                    key = f"key{i}"
                    cache.put(key, i)
                    time.sleep(0.001)  # Simulate work
                    value = cache.get(key)
                    if value is not None and value != i:
                        errors.append(f"Value mismatch for {key}")
            except Exception as e:
                errors.append(str(e))
        
        threads = [
            threading.Thread(target=worker, args=((i//2) * 20, 20))
            for i in range(10)
        ]
        
        for t in threads:
            t.start()
        for t in threads:
            t.join()
        print(cache.data)
        self.assertEqual(len(errors), 0, f"Thread safety errors: {errors}")
        self.assertLessEqual(cache.size(), 100)

if __name__ == '__main__':
    unittest.main()
    test_cache = TestCache()
    test_cache.test_lru_cache()
    test_cache.test_priority_cache()
    test_cache.test_thread_safety()