package SocialNetworkAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncSolver {

    private static Map<String, UserNode> map = new HashMap<>();

    private SyncSolver() {}

    public static void solve(Map<String, UserNode> mapIn) {
        map = mapIn;
        for(UserNode node : map.values()) {
            calculateSync(node);
            calculateNorm(node);
        }
    }

    private static void calculateSync(UserNode node) {
        int pairNum = 0;
        double simSum = 0;
        double sync = 0;
        List<String> outputList = node.getOutputList();
        for(int i = 0; i < outputList.size(); i++) {
            UserNode node1 = map.get(outputList.get(i));
            for(int j = i + 1; j < outputList.size(); j++) {
                UserNode node2 = map.get(outputList.get(j));
                if(isSimilar(node1, node2)) {
                    simSum ++;
                }
                pairNum ++;
            }
        }
        if(pairNum > 0) {
            sync = simSum / pairNum;
        }
        node.setSynchronicity(sync);
    }

    private static void calculateNorm(UserNode node) {
        double simSum = 0;
        double norm = 0;
        List<String> outputList = node.getOutputList();
        int pairNum = outputList.size() * (map.size() - 1);
        for(String indexNode1 : outputList) {
            UserNode node1 = map.get(indexNode1);
            for(UserNode node2 : map.values()) {
                if(node1 != node2 && isSimilar(node1, node2)) {
                    simSum ++;
                }
            }
        }
        if(pairNum > 0) {
            norm = simSum / pairNum;
        }
        node.setNormality(norm);
    }

    private static boolean isSimilar(UserNode node1, UserNode node2) {
        return true;
    }
}
