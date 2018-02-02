package SocialNetworkAnalysis;

import java.io.*;
import java.util.Properties;

/**
 * A class of properties configuration and validation
 * Members in this class were static. Properties can be called directly
 */
public class PropertiesUtil {

    private static String filePath = PropertiesUtil.class.getResource("").getPath();
    private static Properties properties = new Properties();

    private PropertiesUtil() {}

    private static void resetProperties() {
        // Reset the properties file
        try(OutputStream out = new FileOutputStream(filePath + "properties.txt")) {
            properties.setProperty("filePath", "/Users/hajiang2/Documents/CatchSync/test.txt");
            properties.setProperty("maxIter", "100");
            properties.setProperty("minError", "0.001");
            properties.setProperty("gridBase", "2");
            properties.setProperty("xGridsNum", "40");
            properties.setProperty("yGridsNum", "80");
            properties.store(out, "Properties for Social Network Analysis.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {

        try(InputStream in = new FileInputStream(filePath + "properties.txt")) {
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties.getProperty(key);
    }

    public static void checkProperties() {

        try(InputStream in = new FileInputStream(filePath + "properties.txt")) {
            properties.load(in);
        } catch (IOException IO) {
            resetProperties();
            System.out.println("Property file was missing in the given path. " +
                    "New file containing default properties has been built for the solution.");
        }

        String remind = "Please correct it or delete the wrong file and run again to obtain the default properties.";

        try {
            int maxIter = Integer.parseInt(properties.getProperty("maxIter"));
            if(maxIter <= 0) {
                throw new NumberFormatException("\"maxIter\" should be a positive integer. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"maxIter\" should be a positive integer. " + remind);
        }

        try {
            double minError = Double.parseDouble(properties.getProperty("minError"));
            if(minError <= 0) {
                throw new NumberFormatException("\"minError\" should be a positive decimal. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"minError\" should be a positive decimal. " + remind);
        }

        try {
            double gridBase = Double.parseDouble(properties.getProperty("gridBase"));
            if(gridBase <= 1) {
                throw new NumberFormatException("\"gridBase\" should be a decimal bigger than 1. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"gridBase\" should be a decimal bigger than 1. " + remind);
        }

        try {
            int xGridsNum = Integer.parseInt(properties.getProperty("xGridsNum"));
            if(xGridsNum <= 0) {
                throw new NumberFormatException("\"xGridsNum\" should be a positive integer. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"xGridsNum\" should be a positive integer. " + remind);
        }

        try {
            int yGridsNum = Integer.parseInt(properties.getProperty("yGridsNum"));
            if(yGridsNum <= 0) {
                throw new NumberFormatException("\"yGridsNum\" should be a positive integer. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"maxIter\" should be a positive integer. " + remind);
        }
    }
}
