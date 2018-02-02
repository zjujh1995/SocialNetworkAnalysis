package SocialNetworkAnalysis;

import java.util.Map;

public class Client {

    public static void main(String[] args) {

        PropertiesUtil.checkProperty();

        String filePath = PropertiesUtil.getProperty("filePath");
        Map<String, UserNode> map = DataReader.readFile(filePath);
        HitsSolver.solve(map);
        SyncSolver.solve(map);

        map.forEach((k, v) -> System.out.println("nodeId: " + k
                + "  inputDegree: " + v.getInputDegree()
                + "  outputDegree: " + v.getOutputDegree()
                + "  hubness: " + String.format("%.4f", v.getHubness())
                + "  authness " + String.format("%.4f", v.getAuthness())
                + "  sync: " + String.format("%.4f", v.getSynchronicity())
                + "  norm: " + String.format("%.4f", v.getNormality())));
    }
}
