package SocialNetworkAnalysis;

import java.io.*;
import java.util.Properties;

/**
 * A class of properties configuration and validation
 * Members in this class were static. Properties can be called directly
 */
public class PropertiesUtil {

    private static String propertiesPath = PropertiesUtil.class.getResource("").getPath();
    private static Properties properties = new Properties();
    private static String filePath;
    private static String dataFileName;
    private static int maxIter;
    private static double minError;
    private static double gridBase;
    private static int xGridsNum;
    private static int yGridsNum;
    private static int minOutputDegree;
    private static double alpha;

    private PropertiesUtil() {}

    private static void resetProperties() {
        // Reset the properties file
        try(OutputStream out = new FileOutputStream(propertiesPath + "properties.txt")) {
            properties.setProperty("filePath", "/Users/hajiang2/Documents/CatchSync/");
            properties.setProperty("dataFileName", "wiki-Vote.txt");
            properties.setProperty("maxIter", "100");
            properties.setProperty("minError", "0.001");
            properties.setProperty("gridBase", "2");
            properties.setProperty("xGridsNum", "40");
            properties.setProperty("yGridsNum", "80");
            properties.setProperty("minOutputDegree", "5");
            properties.setProperty("alpha", "3");
            properties.store(out, "Properties for Social Network Analysis.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initProperties() {

        try(InputStream in = new FileInputStream(propertiesPath + "properties.txt")) {
            properties.load(in);
        } catch (IOException IO) {
            resetProperties();
            System.out.println("Property file was missing in the given path. " +
                    "New file containing default properties has been built for the solution.");
        }

        String remind = "Please correct it or delete the wrong file and run again to obtain the default properties.";

        try {
            filePath = properties.getProperty("filePath");
        } catch (Exception e) {
            throw new NumberFormatException("\"filePath\" is incorrect. " + remind);
        }

        try {
            dataFileName = properties.getProperty("dataFileName");
        } catch (Exception e) {
            throw new NumberFormatException("\"dataFileName\" is incorrect. " + remind);
        }

        try {
            maxIter = Integer.parseInt(properties.getProperty("maxIter"));
            if(maxIter <= 0) {
                throw new NumberFormatException("\"maxIter\" should be a positive integer. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"maxIter\" should be a positive integer. " + remind);
        }

        try {
            minError = Double.parseDouble(properties.getProperty("minError"));
            if(minError <= 0) {
                throw new NumberFormatException("\"minError\" should be a positive decimal. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"minError\" should be a positive decimal. " + remind);
        }

        try {
            gridBase = Double.parseDouble(properties.getProperty("gridBase"));
            if(gridBase <= 1) {
                throw new NumberFormatException("\"gridBase\" should be a decimal bigger than 1. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"gridBase\" should be a decimal bigger than 1. " + remind);
        }

        try {
            xGridsNum = Integer.parseInt(properties.getProperty("xGridsNum"));
            if(xGridsNum <= 0) {
                throw new NumberFormatException("\"xGridsNum\" should be a positive integer. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"xGridsNum\" should be a positive integer. " + remind);
        }

        try {
            yGridsNum = Integer.parseInt(properties.getProperty("yGridsNum"));
            if(yGridsNum <= 0) {
                throw new NumberFormatException("\"yGridsNum\" should be a positive integer. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"yGridsNum\" should be a positive integer. " + remind);
        }

        try {
            minOutputDegree = Integer.parseInt(properties.getProperty("minOutputDegree"));
            if(minOutputDegree <= 0) {
                throw new NumberFormatException("\"minOutputDegree\" should be a positive integer. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"minOutputDegree\" should be a positive integer. " + remind);
        }

        try {
            alpha = Double.parseDouble(properties.getProperty("alpha"));
            if(alpha < 0) {
                throw new NumberFormatException("\"alpha\" should not be negative. " + remind);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"alpha\" should not be negative. " + remind);
        }
    }

    public static String getFilePath() {
        return filePath;
    }

    public static String getDataFileName() {
        return dataFileName;
    }

    public static int getMaxIter() {
        return maxIter;
    }

    public static double getMinError() {
        return minError;
    }

    public static double getGridBase() {
        return gridBase;
    }

    public static int getxGridsNum() {
        return xGridsNum;
    }

    public static int getyGridsNum() {
        return yGridsNum;
    }

    public static int getMinOutputDegree() {
        return minOutputDegree;
    }

    public static double getAlpha() {
        return alpha;
    }
}
