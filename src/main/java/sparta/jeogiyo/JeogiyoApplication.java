package sparta.jeogiyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JeogiyoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeogiyoApplication.class, args);
    }

}
