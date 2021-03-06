package SocialNetworkAnalysis;

import java.util.Map;

public class Client {

    public static void main(String[] args) {
        PropertiesUtil.initProperties();
        String filePath = PropertiesUtil.getFilePath() + PropertiesUtil.getDataFileName();
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
        System.out.println("\nComplete judging nodes. Stage costs " + (stage5Time - stage4Time) + " ms.\n");

        ResultPrinter.print(map);
        long endTime = System.currentTimeMillis();
        System.out.println("\nComplete exporting csv file. Stage costs " + (endTime - stage5Time) + " ms.\n");
        System.out.println("\nTotal cost: " + (endTime - startTime) + " ms.\n");

//        map.forEach((k, v) -> {
//            if(v.getOutputDegree() >= PropertiesUtil.getMinOutputDegree()){
//                System.out.println("nodeId: " + k
//                    + "  inputDegree: " + v.getInputDegree()
//                    + "  outputDegree: " + v.getOutputDegree()
//                    + "  hubness: " + String.format("%.4f", v.getHubness())
//                    + "  authness " + String.format("%.4f", v.getAuthness())
//                    + "  xGridPos " + v.getxGridPos()
//                    + "  yGridPos " + v.getyGridPos()
//                    + "  sync: " + String.format("%.4f", v.getSynchronicity())
//                    + "  norm: " + String.format("%.4f", v.getNormality())
//                    + "  res: " + String.format("%.4f", v.getResidual())
//                    + "  normal: " + v.getJudgement());}});
    }
}
