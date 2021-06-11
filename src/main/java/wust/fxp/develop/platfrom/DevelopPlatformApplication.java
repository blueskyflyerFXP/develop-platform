package wust.fxp.develop.platfrom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DevelopPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevelopPlatformApplication.class, args);
    }

}
