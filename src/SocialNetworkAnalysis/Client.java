package SocialNetworkAnalysis;

import java.util.Map;

public class Client {

    public static void main(String[] args) {
        PropertiesUtil.checkProperties();
        String filePath = PropertiesUtil.getProperty("filePath");
        long startTime = System.currentTimeMillis();

        Map<String, UserNode> map = DataReader.readFile(filePath);
        long stage1Time = System.currentTimeMillis();
        System.out.println("\nComplete reading data. Stage costs " + (stage1Time - startTime) + " ms.\n");

        HitsSolver.solve(map);
        long stage2Time = System.currentTimeMillis();
        System.out.println("\nComplete solving HITS. Stage costs " + (stage2Time - stage1Time) + " ms.\n");

        SyncSolver.solve(map);
        long stage3Time = System.currentTimeMillis();
        System.out.println("\nComplete solving sync and norm. Stage costs " + (stage3Time - stage2Time) + " ms.\n");

        map.forEach((k, v) -> System.out.println("nodeId: " + k
                + "  inputDegree: " + v.getInputDegree()
                + "  outputDegree: " + v.getOutputDegree()
                + "  hubness: " + String.format("%.4f", v.getHubness())
                + "  authness " + String.format("%.4f", v.getAuthness())
                + "  xGridPos " + v.getxGridPos()
                + "  yGridPos " + v.getyGridPos()
                + "  sync: " + String.format("%.4f", v.getSynchronicity())
                + "  norm: " + String.format("%.4f", v.getNormality())));
    }
}
