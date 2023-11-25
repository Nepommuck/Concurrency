import matplotlib.pyplot as plt
from read_data import read_files


# PLOT_LOG = False
PLOT_LOG = True
WIDTH = 0.4

def main():
    data = read_files()

    fig, axs = plt.subplots(nrows = len(data.keys()))
    plt.subplots_adjust(hspace = 0.8)

    for i, number_of_philosophers in enumerate(sorted(data.keys())):
        ax = axs[i]
        ax.set_title(f"{number_of_philosophers} philosophers")
        ax.set_ylabel("Total wait time [s]")
        ax.set_xlabel("Phlosophers")
        if PLOT_LOG:
            ax.set_yscale("log")

        x_values = [k for k in range(1, number_of_philosophers + 1)]
        for j, (implementation, readings) in enumerate(data[number_of_philosophers]):
            ax.bar(
                x = [(k - WIDTH / 2 + j * WIDTH) for k in x_values], 
                height = sorted([time_ms / 1_000 for time_ms in readings]),
                width = WIDTH,
                label = implementation,
            )
        ax.legend()

    plt.show()


main()
