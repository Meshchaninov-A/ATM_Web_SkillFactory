package variables;

import java.util.Properties;

public class ServiceProperties extends LoadProperties {
    private static final Properties serviceProperties;

    static {
        serviceProperties = getServiceProperties();
    }


    public static final String IP_ADDRESS = serviceProperties.getProperty("server.ip");
    public static final String PORT = serviceProperties.getProperty("server.port");
    public static final String BASE_CARDS_FILE = serviceProperties.getProperty("card.base.file.path");

}
