package SocialNetworkAnalysis;

import java.util.Map;

public class ResSolver {

    private static int xGridsNum = PropertiesUtil.getxGridsNum();
    private static int yGridsNum = PropertiesUtil.getyGridsNum();
    private static int gridsNum = xGridsNum * yGridsNum;
    private static double bgSync;
    private static double resMean;
    private static double resStd;

    private ResSolver() {}

    public static void solve(Map<String, UserNode> map) {
        bgSync = SyncSolver.dotProduct(SyncSolver.getBgVector(), SyncSolver.getBgVector());
        calcRes(map);
    }

    private static double syncLowerBound(double norm) {
        return (- gridsNum * norm * norm + 2 * norm - bgSync) / (1 - gridsNum * bgSync);
    }

    private static void calcRes(Map<String, UserNode> map) {
        double resSum = 0;
        double resSSE = 0;
        int minOutputDegree = PropertiesUtil.getMinOutputDegree();
        int legalNum = 0;  // Only counts nodes whose outputDegree >= minOutputDegree
        for(UserNode node : map.values()) {
            double norm = node.getNormality();
            double res = node.getSynchronicity() - syncLowerBound(norm);
            if(node.getOutputDegree() >= minOutputDegree) {
                node.setResidual(res);
                resSum += res;
                legalNum ++;
            }
        }
        if(legalNum == 0) {
            throw new IllegalArgumentException("OutputDegrees of all the nodes were smaller than \"minOutputDegree\"("
                    + minOutputDegree + "). Therefore, no nodes were considered abnormal.");
        }
        resMean = resSum / legalNum;
        for(UserNode node : map.values()) {
            if(node.getOutputDegree() >= minOutputDegree) {
                resSSE += Math.pow(node.getResidual() - resMean, 2);
            }
        }
        resStd = Math.sqrt(resSSE / legalNum);
        System.out.println("resMean: " + resMean + "  resStd: " + resStd);
    }

    public static int getGridsNum() {
        return gridsNum;
    }

    public static double getBgSync() {
        return bgSync;
    }

    public static double getResMean() {
        return resMean;
    }

    public static double getResStd() {
        return resStd;
    }
}
