from threading import Lock
from typing import Dict
class RequestCounters:
    def __init__(self, totalWindowTime:int = 60, bucketWindowTime:int = 10, limit=100):
        self.bucketCounts: Dict[int, int] = dict()  # Maps bucket timestamp to count of requests in that bucket
        self.totalCounts = 0 # total count of request across all buckets
        self.lastResetTimestamp = 0
        self.totalWindowTime = totalWindowTime # total sliding window time ( in seconds ) # if constant for all users, move out
        self.bucketTime = bucketWindowTime # time window for each bucket ( in seconds )
        self.limit = limit
        self.lock = Lock()


    def getBucketKeyForTimestamp(self, timestamp:int, ) -> int:
        """
        Get the bucket key for the given timestamp
        :param timestamp: request's timestamp in seconds
        :return: bucket key (int) for the given timestamp
        """
        return (timestamp // self.bucketTime)*self.bucketTime

    def getOldestValidBucket(self, timestamp:int) -> int:
        """
        Get the oldest valid bucket key for the given timestamp
        :param timestamp: request's timestamp in seconds
        :return: oldest valid bucket key (int) for the given timestamp
        """
        return self.getBucketKeyForTimestamp(timestamp - self.totalWindowTime)


    def evictOutdatedBuckets(self, timestamp:int):
        """
        Evict outdated buckets based on the current timestamp
        No need for lock here as this is called only after the request has been processed and no issues if the resource is deleted multiple times
        :param timestamp: request's timestamp in seconds
        """
        oldestValidBucket = self.getOldestValidBucket(timestamp)
        keysToRemove = [key for key in self.bucketCounts if key < oldestValidBucket]
        for key in keysToRemove:
            try:
                count = self.bucketCounts[key]
                del self.bucketCounts[key]
                self.totalCounts -= count
            except KeyError:
                # If the key is not found, continue
                continue

