import json
import os.path


IMPLEMENTATIONS = ["asym", "conductor"]
NUMBERS_OF_PHILOSOPHERS = [5, 8, 10]

def read_files():
    results = {num: [] for num in NUMBERS_OF_PHILOSOPHERS}

    data = [(impl, num, f"{impl}-{num}.json") for impl in IMPLEMENTATIONS for num in NUMBERS_OF_PHILOSOPHERS]
    parent_dir = os.path.dirname(os.path.abspath(__file__))

    for impl, num, filename in data:
        filepath = os.path.join(parent_dir, "..", "results", filename)
        file = open(filepath)
        json_data = json.load(file)
        file.close()

        results[num].append((impl, json_data))
    
    return results
