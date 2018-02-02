package SocialNetworkAnalysis;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * A class of properties configuration
 * Members in this class were static
 * Properties can be called directly
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
            properties.setProperty("xAxisGrids", "40");
            properties.setProperty("yAxisGrids", "80");
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

    public static void checkProperty() {

        try(InputStream in = new FileInputStream(filePath + "properties.txt")) {
            properties.load(in);
        } catch (IOException IO) {
            resetProperties();
            System.out.println("Property file was missing in the given path. " +
                    "New file containing default properties has been built for the solution.");
        }

        properties.forEach((key, value) -> {
            if(value == null) {
                throw new NoSuchElementException("\"" + key + "\" is missing in the property file." +
                        " Please correct it or delete the wrong file and run again to obtain the default properties.");
            }
        });

        int maxIter = Integer.parseInt(properties.getProperty("maxIter"));
        double minError = Double.parseDouble(properties.getProperty("minError"));
        double gridBase = Double.parseDouble(properties.getProperty("gridBase"));
        int xAxisGrids = Integer.parseInt(properties.getProperty("xAxisGrids"));
        int yAxisGrids = Integer.parseInt(properties.getProperty("yAxisGrids"));

        if(maxIter <= 0) {
            throw new NumberFormatException("\"maxIter\" should be a positive integer. ");
        }
        if(minError <= 0) {
            throw new NumberFormatException("\"minError\" should be a positive decimal.");
        }
        if(gridBase <= 1) {
            throw new NumberFormatException("\"gridBase\" should be a decimal bigger than 1. ");
        }
        if(xAxisGrids <= 0) {
            throw new NumberFormatException("\"xAxisGrid\" should be a positive integer. ");
        }
        if(yAxisGrids <= 0) {
            throw new NumberFormatException("\"yAxisGrid\" should be a positive integer. ");
        }
    }
}
