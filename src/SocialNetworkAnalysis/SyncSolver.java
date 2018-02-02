package SocialNetworkAnalysis;

import java.util.List;
import java.util.Map;

public class SyncSolver {

    private SyncSolver() {}

    public static void solve(Map<String, UserNode> map) {
        calculateGridPos(map);
        calculateSync(map);
        calculateNorm(map);
    }

    private static void calculateGridPos(Map<String, UserNode> map) {
        double gridBase = Double.parseDouble(PropertiesUtil.getProperty("gridBase"));
        int xGridsNum = Integer.parseInt(PropertiesUtil.getProperty("xGridsNum"));
        int yGridsNum = Integer.parseInt(PropertiesUtil.getProperty("yGridsNum"));
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
        }
    }

    private static boolean isSimilar(UserNode node1, UserNode node2) {
        return node1.getxGridPos() == node2.getxGridPos() && node1.getyGridPos() == node2.getyGridPos();
    }

    private static void calculateSync(Map<String, UserNode> map) {
        for(UserNode node : map.values()) {
            int pairNum = 0;
            double simSum = 0;
            List<String> outputList = node.getOutputList();
            for (int i = 0; i < outputList.size(); i++) {
                UserNode node1 = map.get(outputList.get(i));
                for (int j = i + 1; j < outputList.size(); j++) {
                    UserNode node2 = map.get(outputList.get(j));
                    if (isSimilar(node1, node2)) {
                        simSum++;
                    }
                    pairNum++;
                }
            }
            if (pairNum > 0) {
                double sync = simSum / pairNum;
                node.setSynchronicity(sync);
            }
        }
    }

    private static void calculateNorm(Map<String, UserNode> map) {
        for(UserNode node : map.values()) {
            double simSum = 0;
            List<String> outputList = node.getOutputList();
            int pairNum = outputList.size() * (map.size() - 1);
            for (String indexNode1 : outputList) {
                UserNode node1 = map.get(indexNode1);
                for (UserNode node2 : map.values()) {
                    if (node1 != node2 && isSimilar(node1, node2)) {
                        simSum++;
                    }
                }
            }
            if (pairNum > 0) {
                double norm = simSum / pairNum;
                node.setNormality(norm);
            }
        }
    }
}
