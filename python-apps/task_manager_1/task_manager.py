from sortedcontainers import SortedDict, SortedList
from user import User
from task import Task
class TaskManger:
    def __init__(self):
        self.tasks:dict = {}
        self.sorted_task_ids:SortedList[(str, str)] = SortedList(key=lambda item: item[0])
        self.users = {}

    def add_task(self, title, user_id, priority):
        user = self.users[user_id]
        if user is None:
            raise ValueError("User not found")
        new_task = Task(user, title, priority)
        self.tasks[new_task.id] = new_task
        self.sorted_task_ids.add((priority, new_task.id))
        print("Added new task" + str(new_task.id))
    def modify_task(self, task_id, new_priority):
        if task_id not in self.tasks:
            raise ValueError("Task not found")
        task = self.tasks.pop(task_id)
        self.sorted_task_ids.discard((task.priority, task.id))

        # add back
        task.priority = new_priority
        self.tasks[task_id] = task
        self.sorted_task_ids.add((task.priority, task.id))
        print("Updated task")

    def execute_task(self):
        _,task_id = self.sorted_task_ids.pop()
        top_task = self.tasks.pop(task_id)

        top_task.is_completed = True
        print(top_task)
        res =  (top_task.id, top_task.user.id)
        del top_task
        print("Executed task" + str(res[0]) + str(res[1]))
        return res

    def remove_task(self, task_id):
        if task_id not in self.tasks:
            raise ValueError("Task not found")
        task = self.tasks.pop(task_id)
        self.sorted_task_ids.discard((task.priority, task.id))
    def add_users(self, name):
        new_user = User(name)
        self.users[new_user.id] = new_user



