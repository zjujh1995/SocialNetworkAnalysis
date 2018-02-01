package SocialNetworkAnalysis;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HitsSolver {

    private String filePath;
    private Map<String, UserNode> map = new HashMap<>();

    public void solve(String filePath) {
        this.filePath = filePath;
        readFile();
        calculateHits();
    }

    public Map<String, UserNode> getMap() {
        return map;
    }

    private void readFile() {
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
    }

    private void calculateHits() {
        // Parameters used to control the iteration
        int maxIter = Integer.parseInt(PropertiesUtil.getProperty("maxIter"));
        double minError = Double.parseDouble(PropertiesUtil.getProperty("minError"));
        // Start iterations
        System.out.println("Start calculating HITS. (Maximum " + maxIter + " iterations)");
        for(int i = 0; i < maxIter; i++) {

            // Used for normalization
            double maxHub = 0;
            double maxAuth = 0;
            double error = 0;

            // Update hubness and authness iteratively
            for(UserNode node : map.values()) {
                double hub = node.getHubness();
                double auth = node.getAuthness();
                // Update temp authness of the nodes in the outputList
                for(String outputId : node.getOutputList()) {
                    UserNode outputNode = map.get(outputId);
                    outputNode.incTempAuthness(hub);
                    maxAuth = Math.max(maxAuth, outputNode.getTempAuthness());         // Update the maximum authness
                }
                // Update temp hubness of the nodes in the inputList
                for(String inputId : node.getInputList()) {
                    UserNode inputNode = map.get(inputId);
                    inputNode.incTempHubness(auth);
                    maxHub = Math.max(maxHub, inputNode.getTempHubness());             // Update the maximum hubness
                }
            }

            for(UserNode node : map.values()) {
                // Normalize temp values
                node.divTempHubness(maxHub);
                node.divTempAuthness(maxAuth);

                error = Math.max(error, Math.abs(node.getHubness() - node.getTempHubness()));
                error = Math.max(error, Math.abs(node.getAuthness() - node.getTempAuthness()));

                // Pull temp values into real values and reset temp values into zero
                node.refresh();
            }

            // Early stop when the error can be accepted
            if(error < minError) {
                System.out.println("Error of HITS is accepted after " + (i + 1) + " iterations.");
                break;
            }
        }
    }

}
