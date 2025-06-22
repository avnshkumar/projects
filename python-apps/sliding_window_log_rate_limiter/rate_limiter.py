from threading import Lock
from time import sleep
from typing import Dict
from request_counters import RequestCounters

type UserId = str
class RateLimiter:
    def __init__(self):
        self.lock = Lock()
        self.userRequestMap: Dict[UserId, RequestCounters] = dict()

    def addUser(self, user_id: UserId, total_window_time:int, bucket_window_time :int):
        with self.lock:
            if user_id not in self.userRequestMap:
                self.userRequestMap[user_id] = RequestCounters(total_window_time, bucket_window_time)
            else:
                raise ValueError(f"User {user_id} already exists in the rate limiter.")

    def removeUser(self, user_id: UserId):
        with self.lock:
            if user_id in self.userRequestMap:
                del self.userRequestMap[user_id]
            else:
                ValueError(f"User {user_id} does not exist in the rate limiter.")
    def checkIfUserExists(self, user_id: UserId) -> bool:
        """
        Check if the user exists in the rate limiter
        :param user_id: parsed userId of the request
        :return: True if user exists, False otherwise
        """
        with self.lock:
            return user_id in self.userRequestMap

    def getCuurentTimeStampInSeconds(self) -> int:
        """
        Get the current timestamp in seconds
        :return: current timestamp in seconds
        """
        from time import time
        return int(round(time()))

    def handleNewApiRequest(self, user_id: UserId) -> bool:
        """
        Handle a new API request for the given user and check if it is allowed based on the rate limiting rules.
        :param user_id: parsed userId of the request
        :return:
        """

        # check if UserExists
        if(not self.checkIfUserExists(user_id)):
            raise ValueError(f"User {user_id} does not exist in the rate limiter. Please add the user first.")
        userLock = self.userRequestMap.get(user_id).lock
        with userLock:
            # get bucket Key for the current timestamp
            request_map = self.userRequestMap.get(user_id)
            current_timestamp = self.getCuurentTimeStampInSeconds()
            bucket_key = request_map.getBucketKeyForTimestamp(current_timestamp)
            # delete old buckets
            # add current timestamp to the request map
            bucket_count = request_map.bucketCounts
            print(bucket_count, "bucket_key", bucket_key, "current_timestamp", current_timestamp)
            if bucket_key not in bucket_count:
                bucket_count[bucket_key] = 1
            else:
                bucket_count[bucket_key] += 1
            request_map.totalCounts +=1
            request_map.evictOutdatedBuckets(current_timestamp)
            if request_map.totalCounts > request_map.limit:
                return False
            return True


rate_limiter = RateLimiter()
user_ids = ["user1", "user2", "user3", "user4", "user5"]
for i in range(5):
    rate_limiter.addUser(user_ids[i], total_window_time=5, bucket_window_time=1)
for j in range(3):
    for i in range(1000):
        if rate_limiter.handleNewApiRequest(user_ids[i%5]):
            print(f"Request allowed for {user_ids[i%5]}")
        else:
            continue
            # print(f"Request denied for {user_ids[i%5]} due to rate limiting.")
    sleep_time = (j+1)*5
    print("Sleeping for ", sleep_time, "seconds")
    if(j!=2):
        sleep(sleep_time)







