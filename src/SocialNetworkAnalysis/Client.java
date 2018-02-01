package SocialNetworkAnalysis;

import java.util.Map;

public class Client {

    public static void main(String[] args) {

        String filePath = PropertiesUtil.getProperty("filePath");

        HitsSolver hits = new HitsSolver();
        hits.solve(filePath);
        Map<String, UserNode> map = hits.getMap();

        map.forEach((k, v) -> System.out.println("ID: " + k + " inputDegree: " + v.getInputDegree()
                + " outputDegree: " + v.getOutputDegree() + " hubness: " + v.getHubness()
                + " authness " + v.getAuthness()));

        SyncSolver sync = new SyncSolver();
        sync.solve(map);
    }
}
