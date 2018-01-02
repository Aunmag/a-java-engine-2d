package aunmag.nightingale.data;

import java.io.InputStream;
import java.util.Properties;

public class DataEngine {

    public static final String TITLE = "Nightingale Engine";
    public static final String TITLE_FULL;
    public static final String VERSION;
    public static final String DEVELOPER = "Aunmag";

    static {
        String filename = "/nightingale-engine.properties";
        String version = "X.X.X";

        try {
            InputStream inputStream = DataEngine.class.getResourceAsStream(filename);
            Properties properties = new Properties();
            properties.load(inputStream);
            version = properties.getProperty("version");
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        VERSION = version;
        TITLE_FULL = String.format("%s v%s by %s", TITLE, VERSION, DEVELOPER);
    }

}
