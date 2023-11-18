import statistics

class Record():
    def __init__(self, number_of_threads: int, number_of_tasks: int, time_measurements_ms: list[float]) -> None:
        self.number_of_threads = number_of_threads
        self.number_of_tasks = number_of_tasks
        self.time_measurements_ms = time_measurements_ms

        self.mean = statistics.mean(time_measurements_ms)
        self.stdev = statistics.stdev(time_measurements_ms)
    
    def __str__(self) -> str:
        return f"<threads = {self.number_of_threads}, tasks = {self.number_of_tasks}, " + \
            f"avg = {self.mean}, stdev = {self.stdev}>"
