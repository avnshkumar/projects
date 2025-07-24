import task_manager
from sortedcontainers import SortedList, SortedKeyList

if __name__=="__main__":
    manager = task_manager.TaskManger()
    manager.add_users("Avinash")
    manager.add_users("User1")
    manager.add_users("User2")
    manager.add_users("User3")
    manager.add_users("User4")
    user_ids = list(manager.users.keys())

    manager.add_task("Task1", user_ids[0], 5)
    manager.add_task("Task2", user_ids[1], 4)
    manager.add_task("Task3", user_ids[2], 3)
    manager.add_task("Task4", user_ids[3], 2)
    manager.add_task("Task5", user_ids[4], 1)
    manager.add_task("Task6", user_ids[0], 0)



    manager.execute_task()
    manager.execute_task()
    task_ids = list(manager.tasks.keys())
    manager.remove_task(task_ids[0])
    manager.remove_task(task_ids[1])

    x = SortedKeyList()
    x.add(5)
    x.add(7)
    x.add(2)
    x.add(100)
    x.add(1)
    print(x)
    x.discard(7)
    x.add(8)
    print(x)






