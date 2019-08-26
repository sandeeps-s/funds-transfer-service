package bank.revolut.fund.transfer;

import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final String PROPERTIES_FILE_NAME = "funds-transfer-service.properties";

    public static final Config instance = new Config();

    private final Properties properties;

    private Config() {

        try {
            properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading:" + PROPERTIES_FILE_NAME);
        }

    }

    public String getPropertyAsString(String key) {

        return properties.getProperty(key);
    }

}
