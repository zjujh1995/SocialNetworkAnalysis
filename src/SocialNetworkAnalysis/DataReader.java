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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            int read = 0;
            int cur = 0;
            int readLimit = PropertiesUtil.getReadLimit();
            int readInterval = PropertiesUtil.getReadInterval();
            while((line = reader.readLine()) != null && read < readLimit) {
                cur ++;
                if(cur == readInterval) {
                    cur = 0;
                    read ++;
                    if(read % 100000 == 0) {
                        System.out.println("Read " + read + " records.");
                    }
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
                }
            }
            System.out.println("\nTotally read " + read + " records.\n");
        }
        catch (IOException io) {
            io.printStackTrace();
        }
        return map;
    }
}
