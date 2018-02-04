package SocialNetworkAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DataReader {

    private DataReader() {}

    public static Map<String, UserNode> readFile(String filePath) {

        Map<String, UserNode> map = new HashMap<>();
        String line;
        int maxNodeNum = 3000000;
        int read = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {

            while((line = reader.readLine()) != null && maxNodeNum > 0) {
                read ++;
                if(read == 1000) {
                    read = 0;
                    String[] idPair = line.split("[ |\t]");
                    String inputId = idPair[0];
                    String outputId = idPair[1];
                    UserNode inputNode;
                    UserNode outputNode;

                    if (!map.containsKey(inputId)) {
                        inputNode = UserNode.instanceByOutputDegree(inputId, 1);
                        map.put(inputId, inputNode);
                    } else {
                        inputNode = map.get(inputId);
                        inputNode.incOutDegree(1);
                    }
                    if (!map.containsKey(outputId)) {
                        outputNode = UserNode.instanceByInputDegree(outputId, 1);
                        map.put(outputId, outputNode);
                    } else {
                        outputNode = map.get(outputId);
                        outputNode.incInputDegree(1);
                    }

                    inputNode.getOutputList().add(outputId);
                    outputNode.getInputList().add(inputId);

                    maxNodeNum--;
                }
            }
            System.out.println("Read " + (3000000 - maxNodeNum) + " nodes.");
        }
        catch (IOException io) {
            io.printStackTrace();
        }
        return map;
    }
}
