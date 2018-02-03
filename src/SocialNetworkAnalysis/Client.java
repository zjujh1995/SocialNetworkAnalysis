package SocialNetworkAnalysis;

import java.util.Map;

public class Client {

    public static void main(String[] args) {
        PropertiesUtil.initProperties();
        String filePath = PropertiesUtil.getFilePath();
        long startTime = System.currentTimeMillis();

        Map<String, UserNode> map = DataReader.readFile(filePath);
        long stage1Time = System.currentTimeMillis();
        System.out.println("\nComplete reading data. Stage costs " + (stage1Time - startTime) + " ms.\n");

        HitsSolver.solve(map);
        long stage2Time = System.currentTimeMillis();
        System.out.println("\nComplete solving HITS. Stage costs " + (stage2Time - stage1Time) + " ms.\n");

        SyncSolver.solve(map);
        long stage3Time = System.currentTimeMillis();
        System.out.println("\nComplete solving syncs and norms. Stage costs " + (stage3Time - stage2Time) + " ms.\n");

        ResSolver.solve(map);
        long stage4Time = System.currentTimeMillis();
        System.out.println("\nComplete solving residuals. Stage costs " + (stage4Time - stage3Time) + " ms.\n");

        NodeJudger.judge(map);
        long stage5Time = System.currentTimeMillis();
        System.out.println("\nComplete solving residuals. Stage costs " + (stage5Time - stage4Time) + " ms.\n");

        map.forEach((k, v) -> System.out.println("nodeId: " + k
                + "  inputDegree: " + v.getInputDegree()
                + "  outputDegree: " + v.getOutputDegree()
                + "  hubness: " + String.format("%.4f", v.getHubness())
                + "  authness " + String.format("%.4f", v.getAuthness())
                + "  xGridPos " + v.getxGridPos()
                + "  yGridPos " + v.getyGridPos()
                + "  sync: " + String.format("%.4f", v.getSynchronicity())
                + "  norm: " + String.format("%.4f", v.getNormality())
                + "  res: " + String.format("%.4f", v.getResidual())
                + "  legal: " + v.getJudgement()));
    }
}
