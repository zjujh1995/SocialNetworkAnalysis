package SocialNetworkAnalysis;

import java.util.ArrayList;
import java.util.List;

public class UserNode {

    private String userId;
    private int outputDegree = 0;
    private int inputDegree = 0;
    private double hubness = 1;
    private double authness = 1;
    private double tempHubness = 0;
    private double tempAuthness = 0;
    private int xGridPos = -1;
    private int yGridPos = -1;
    private double synchronicity = -1;
    private double normality = -1;
    private List<String> inputList = new ArrayList<>();
    private List<String> outputList = new ArrayList<>();

    public UserNode(String userId) {
        this.userId = userId;
    }

    private UserNode(String userId, int outputDegree, int inputDegree) {
        this.userId = userId;
        this.outputDegree = outputDegree;
        this.inputDegree = inputDegree;
    }


    public static UserNode instanceByInputDegree(String userId, int inputDegree) {
        return new UserNode(userId, 0, inputDegree);
    }

    public static UserNode instanceByOutputDegree(String userId, int outputDegree) {
        return  new UserNode(userId, outputDegree, 0);
    }


    public void incOutDegree(int inc) {
        outputDegree += inc;
    }

    public void incInputDegree(int inc) {
        inputDegree += inc;
    }

    public void incTempHubness(double inc) {
        tempHubness += inc;
    }

    public void incTempAuthness(double inc) {
        tempAuthness += inc;
    }

    public void refresh() {
        hubness = tempHubness;
        authness = tempAuthness;
        tempHubness = 0;
        tempAuthness = 0;
    }


    public void setOutputDegree(int outputDegree) {
        this.outputDegree = outputDegree;
    }

    public void setInputDegree(int inputDegree) {
        this.inputDegree = inputDegree;
    }

    public void setHubness(double hubness) {
        this.hubness = hubness;
    }

    public void setAuthness(double authness) {
        this.authness = authness;
    }

    public void divTempHubness(double div) {
        tempHubness /= div;
    }

    public void divTempAuthness(double div) {
        tempAuthness /= div;
    }

    public void setxGridPos(int xGridPos) {
        this.xGridPos = xGridPos;
    }

    public void setyGridPos(int yGridPos) {
        this.yGridPos = yGridPos;
    }

    public void setSynchronicity(double synchronicity) {
        this.synchronicity = synchronicity;
    }

    public void setNormality(double normality) {
        this.normality = normality;
    }

    public void addInputList(String inputId) {
        this.inputList.add(inputId);
    }

    public void addOutputList(String outputId) {
        this.outputList.add(outputId);
    }


    public String getUserId() {
        return userId;
    }

    public int getOutputDegree() {
        return outputDegree;
    }

    public int getInputDegree() {
        return inputDegree;
    }

    public double getHubness() {
        return hubness;
    }

    public double getAuthness() {
        return authness;
    }

    public double getTempHubness() {
        return tempHubness;
    }

    public double getTempAuthness() {
        return tempAuthness;
    }

    public int getxGridPos() {
        return xGridPos;
    }

    public int getyGridPos() {
        return yGridPos;
    }

    public double getSynchronicity() {
        return synchronicity;
    }

    public double getNormality() {
        return normality;
    }

    public List<String> getInputList() {
        return inputList;
    }

    public List<String> getOutputList() {
        return outputList;
    }
}
