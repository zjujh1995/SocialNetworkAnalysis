package SocialNetworkAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DataReader {

    private DataReader() {}

    public static Map<String, UserNode> readFile(String filePath) {

        Map<String, UserNode> map = new HashMap<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            while((line = reader.readLine()) != null) {
                String[] idPair = line.split(" ");
                String inputId = idPair[0];
                String outputId = idPair[1];
                UserNode inputNode;
                UserNode outputNode;

                if(!map.containsKey(inputId)) {
                    inputNode = UserNode.instanceByOutputDegree(inputId, 1);
                    map.put(inputId, inputNode);
                } else {
                    inputNode = map.get(inputId);
                    inputNode.incOutDegree(1);
                }

                if(!map.containsKey(outputId)) {
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
        catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
