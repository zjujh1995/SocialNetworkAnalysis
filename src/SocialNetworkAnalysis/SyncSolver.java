package SocialNetworkAnalysis;

import java.util.List;
import java.util.Map;

public class SyncSolver {

    private static int xGridsNum = PropertiesUtil.getxGridsNum();
    private static int yGridsNum = PropertiesUtil.getyGridsNum();
    private static double[][] bgVector = new double[xGridsNum][yGridsNum];

    private SyncSolver() {}

    public static void solve(Map<String, UserNode> map) {
        bgVector = calcGridPos(map);
        calcSyncNorm(map);
    }

    private static double[][] calcGridPos(Map<String, UserNode> map) {
        double gridBase = PropertiesUtil.getGridBase();
        double bgUnit = 1.0 / map.size();
        for(UserNode node : map.values()) {
            double xCursor = 1;
            node.setxGridPos(1);  // If x is very small, it should be in the leftest column of grids.
            for(int xGridPos = xGridsNum; xGridPos > 1; xGridPos--) {
                xCursor /= gridBase;
                if(node.getAuthness() > xCursor) {
                    node.setxGridPos(xGridPos);
                    break;
                }
            }
            double yCursor = 1;
            node.setyGridPos(yGridsNum);  // If y is very big, it should be in the top row of grids.
            for(int yGridPos = 1; yGridPos < yGridsNum; yGridPos++) {
                if(node.getInputDegree() < yCursor) {
                    node.setyGridPos(yGridPos);
                    break;
                }
                yCursor *= gridBase;
            }
            bgVector[node.getxGridPos() - 1][node.getyGridPos() - 1] += bgUnit;
        }
        return bgVector;
    }

    private static void calcSyncNorm(Map<String, UserNode> map) {
        for(UserNode node : map.values()) {
            List<String> outputList = node.getOutputList();
            double[][] fgVector = new double[xGridsNum][yGridsNum];
            double fgUnit = 1.0 / outputList.size();
            for(String outputId : outputList) {
                UserNode fgNode = map.get(outputId);
                fgVector[fgNode.getxGridPos() - 1][fgNode.getyGridPos() - 1] += fgUnit;
            }
            if(outputList.size() >= PropertiesUtil.getMinOutputDegree()) {
                double sync = dotProduct(fgVector, fgVector);
                double norm = dotProduct(fgVector, bgVector);
                node.setSynchronicity(sync);
                node.setNormality(norm);
            }
        }
    }

    public static double dotProduct(double[][] vector1, double[][] vector2) {
        double dot = 0;
        for(int i = 0; i < vector1.length; i++) {
            for(int j = 0; j < vector1[i].length; j++) {
                dot += vector1[i][j] * vector2[i][j];
            }
        }
        return dot;
    }

    public static double[][] getBgVector() {
        return bgVector;
    }

//    private static boolean isSimilar(UserNode node1, UserNode node2) {
//        return node1.getxGridPos() == node2.getxGridPos() && node1.getyGridPos() == node2.getyGridPos();
//    }
//
//    private static void appCalcSync(Map<String, UserNode> map) {
//        for(UserNode node : map.values()) {
//            int pairNum = 0;
//            double simSum = 0;
//            List<String> outputList = node.getOutputList();
//            for(int i = 0; i < outputList.size(); i++) {
//                UserNode node1 = map.get(outputList.get(i));
//                for (int j = i + 1; j < outputList.size(); j++) {
//                    UserNode node2 = map.get(outputList.get(j));
//                    if(isSimilar(node1, node2)) {
//                        simSum++;
//                    }
//                    pairNum++;
//                }
//            }
//            if(pairNum > 0) {
//                double sync = simSum / pairNum;
//                node.setSynchronicity(sync);
//            }
//        }
//    }
//
//    private static void appCalcNorm(Map<String, UserNode> map) {
//        for(UserNode node : map.values()) {
//            double simSum = 0;
//            List<String> outputList = node.getOutputList();
//            int pairNum = outputList.size() * (map.size() - 1);
//            for (String indexNode1 : outputList) {
//                UserNode node1 = map.get(indexNode1);
//                for (UserNode node2 : map.values()) {
//                    if (node1 != node2 && isSimilar(node1, node2)) {
//                        simSum++;
//                    }
//                }
//            }
//            if (pairNum > 0) {
//                double norm = simSum / pairNum;
//                node.setNormality(norm);
//            }
//        }
//    }
}
