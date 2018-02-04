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
    auth = np.array(df_result["authness"]).tolist()
    input = np.array(df_result["inputDegree"]).tolist()
    plt.xscale("log")
    plt.yscale("log")
    plt.loglog(auth, input, marker='o', color='g', label="normal_nodes", linewidth=0)
    # plt.hist2d(auth, input)
    # plt.colorbar()
    plt.xlabel("authness")
    plt.ylabel("intputDegree")
    # plt.xlim(0, 0.1)
    # plt.ylim(1e0)


def plot_baseline():
    plt.subplot(1, 1, 1)
    x = np.linspace(0, 0.3, 1000)
    y = cal_baseline(x)
    plt.plot(x, y, color="r")


def plot_norm_sync():
    plt.subplot(1, 1, 1)
    normal_norm = np.array(df_normal["normality"]).tolist()
    normal_sync = np.array(df_normal["synchronicity"]).tolist()
    abnormal_norm = np.array(df_abnormal["normality"]).tolist()
    abnormal_sync = np.array(df_abnormal["synchronicity"]).tolist()
    # plt.scatter(normal_norm, normal_sync, marker='o', color='g', label="normal_nodes")
    # plt.scatter(abnormal_norm, abnormal_sync, marker='x', color='r', label="abnormal_nodes")
    norm = np.array(df_result["normality"]).tolist()
    sync = np.array(df_result["synchronicity"]).tolist()
    plt.hist2d(norm, sync, bins=100, norm=LogNorm())
    plt.colorbar()
    plt.xlabel("normality")
    plt.ylabel("synchronicity")
    plt.ylim(0)


def cal_baseline(norm):
    return (- GRIDS_NUM * norm * norm + 2 * norm - BG_SYNC) / (1 - GRIDS_NUM * BG_SYNC)


if __name__ == "__main__":
    # plot_input_auth()
    plot_baseline()
    plot_norm_sync()
    plt.show()

