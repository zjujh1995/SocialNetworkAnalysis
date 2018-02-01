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
            properties.store(out, "Properties for Social Network Analysis.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {

        try(InputStream in = new FileInputStream(filePath + "properties.txt")) {
            properties.load(in);
        } catch (IOException IO) {
            resetProperties();
            return getProperty(key);
        }

        String value = properties.getProperty(key);

        if(value == null) {
            throw new NoSuchElementException("\nProperty of \"" + key + "\" was missing in the file. " +
                    "Please correct it or delete the wrong file and run again to obtain the default properties.");
        } else {
            return value;
        }
    }
}
