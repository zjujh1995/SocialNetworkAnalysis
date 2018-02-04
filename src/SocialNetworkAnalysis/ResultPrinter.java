package SocialNetworkAnalysis;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import java.io.*;
import java.util.Map;

public class ResultPrinter {

    public static void print(Map<String, UserNode> map) {
        String filePath = PropertiesUtil.getFilePath();
        try(FileWriter fw = new FileWriter(new File(filePath + "result.csv"))) {
            CsvWriter writer = new CsvWriter(fw, new CsvWriterSettings());
            writer.writeHeaders("nodeId", "inputDegree", "outputDegree", "hubness", "authness", "xGridPos",
                    "yGridPos", "synchronicity", "normality", "residual", "normal");
            for(UserNode node : map.values()) {
                if(node.getOutputDegree() >= PropertiesUtil.getMinOutputDegree()) {
                    Object array[] = new Object[11];
                    array[0] = node.getUserId();
                    array[1] = node.getInputDegree();
                    array[2] = node.getOutputDegree();
                    array[3] = node.getHubness();
                    array[4] = node.getAuthness();
                    array[5] = node.getxGridPos();
                    array[6] = node.getyGridPos();
                    array[7] = node.getSynchronicity();
                    array[8] = node.getNormality();
                    array[9] = node.getResidual();
                    array[10] = node.getJudgement();
                    writer.writeRow(array);
                }
            }
            writer.flush();
            writer.close();
        } catch(IOException io) {
            io.printStackTrace();
        }

        try(FileWriter fw = new FileWriter(new File(filePath + "lineParas.csv"))) {
            CsvWriter writer = new CsvWriter(fw, new CsvWriterSettings());
            writer.writeHeaders("gridsNum", "bgSync");
            writer.writeRow(ResSolver.getGridsNum(), ResSolver.getBgSync());
            writer.flush();
            writer.close();
        } catch(IOException io) {
            io.printStackTrace();
        }
    }
}
