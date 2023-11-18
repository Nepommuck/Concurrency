


import os
import os.path
import tkinter as tk

from read_data import read_data, convert_json_data, group_by_number_of_threads
from TableApp import TableApp


def main():
    parent_dir = os.path.dirname(os.path.abspath(__file__))
    json_data = read_data(
        filepath = os.path.join(parent_dir, "..", "exports", "data.json"),
    )
    typed_data = convert_json_data(json_data)

    for record in typed_data:
        print(record)

    grouped_records = group_by_number_of_threads(typed_data)

    print(grouped_records)

    root = tk.Tk()
    app = TableApp(root, grouped_records)
    root.mainloop()


main()
