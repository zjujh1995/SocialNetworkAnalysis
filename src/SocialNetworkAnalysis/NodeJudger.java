package SocialNetworkAnalysis;

import java.util.Map;

public class NodeJudger {

    private NodeJudger() {}

    public static void judge(Map<String, UserNode> map) {
        double threshold = ResSolver.getResMean() + ResSolver.getResStd() * PropertiesUtil.getAlpha();
        for(UserNode node : map.values()) {
            if(node.getResidual() > threshold && node.getOutputDegree() >= PropertiesUtil.getMinOutputDegree()) {
                node.setJudgement(false);
            }
        }
    }
}
