import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APP {
    private static final Logger LOG = LoggerFactory.getLogger(APP.class);

    public static void main(String[] args) {
        LOG.info("info");
        LOG.debug("debug");
        LOG.error("error");
        System.out.println("Hello World!");
        }
}

