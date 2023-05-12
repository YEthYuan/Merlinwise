package pro.yeyuan.merlinwisewiki.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@ComponentScan("pro.yeyuan")
@SpringBootApplication
public class MerlinwiseWikiApplication {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(MerlinwiseWikiApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MerlinwiseWikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("Successfully launch the app!!!");
        LOG.info("Visit: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
