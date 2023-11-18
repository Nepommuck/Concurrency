import json

from Record import Record


def read_data(filepath: str) -> list[dict]:
    file = open(filepath)
    json_data = json.load(file)
    file.close()

    return json_data


def convert_json_data(json_data: list[dict]) -> list[Record]:
    return [Record(
        number_of_threads = rec_dict["numberOfThreads"],
        number_of_tasks = rec_dict["numberOfTasks"],
        time_measurements_ms = rec_dict["timeMeasurementsMs"],
    ) for rec_dict in json_data]


def group_by_number_of_threads(typed_data: list[Record]) -> dict[int, list[Record]]:
    result_dict = {}
    for record in typed_data:
        result_dict[record.number_of_threads] = result_dict.get(record.number_of_threads, []) + [record]
    
    for key in result_dict.keys():
        result_dict[key].sort(key = lambda record: record.number_of_tasks)
    
    return result_dict
