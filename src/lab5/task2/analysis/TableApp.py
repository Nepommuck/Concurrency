from tkinter import ttk

from Record import Record


class TableApp:
    def __init__(self, root, dict_data: dict[int, list[Record]]):
        self.root = root
        self.root.title("Measurement results")

        thread_numbers = sorted(dict_data.keys())
        columns_names = ["TASKS \ THREADS"] + thread_numbers

        # Create a Treeview widget
        self.tree = ttk.Treeview(root, columns=columns_names, show="headings")

        # Define column headings
        for column in columns_names:
            self.tree.heading(column, text=column)

        table = [[None for _ in range(4)] for _ in range(3)]

        row_names = [
            "= THREADS",
            "= 10 * THREADS",
            "= PIXELS",
        ]
        for i, name in enumerate(row_names):
            table[i][0] = name

        for x, thread_number in enumerate(thread_numbers):
            for y, record in enumerate(dict_data[thread_number]):
                table[y][x + 1] = f"{round(record.mean)} +/- {round(record.stdev)} ms"

        for row in table:
            self.tree.insert("", "end", values=row)

        self.tree.pack(pady=10)
