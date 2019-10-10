package community.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ContentApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(ContentApiApplication.class, args);
  }
}
