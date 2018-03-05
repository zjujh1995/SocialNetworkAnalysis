import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.colors import LogNorm

df_para = pd.read_csv("/Users/hajiang2/Documents/CatchSync/lineParas.csv")
df_result = pd.read_csv("/Users/hajiang2/Documents/CatchSync/result.csv")
df_normal = df_result[df_result["normal"] == True]
df_abnormal = df_result[df_result["normal"] == False]

GRIDS_NUM = df_para.loc[0]["gridsNum"]
BG_SYNC = df_para.loc[0]["bgSync"]
plt.figure(figsize=(12, 8))


def plot_input_auth():
    plt.subplot(2, 1, 1)
    auth = np.array(df_result["authness"])
    input = np.array(df_result["inputDegree"])
    plt.xscale("log")
    plt.yscale("log")
    plt.loglog(auth, input, marker='o', color='g', label="normal_nodes", linewidth=0)
    plt.xlabel("authness")
    plt.ylabel("intputDegree")



def plot_baseline():
    plt.subplot(2, 1, 2)
    x = np.linspace(0, 0.3, 1000)
    y = cal_baseline(x)
    plt.plot(x, y, color="r")


def plot_norm_sync():
    # plt.subplot(2, 1, 2)
    norm = np.array(df_result["normality"])
    sync = np.array(df_result["synchronicity"])
    plt.hist2d(norm, sync, bins=(100, 50), norm=LogNorm())
    plt.colorbar()
    plt.xlabel("normality")
    plt.ylabel("synchronicity")
    plt.ylim(0)


def cal_baseline(norm):
    return (- GRIDS_NUM * norm * norm + 2 * norm - BG_SYNC) / (1 - GRIDS_NUM * BG_SYNC)


if __name__ == "__main__":
    # plot_input_auth()
    # plot_baseline()
    plot_norm_sync()
    plt.show()

