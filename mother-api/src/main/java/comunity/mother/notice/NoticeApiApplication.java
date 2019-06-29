package comunity.mother.notice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class NoticeApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(NoticeApiApplication.class, args);
  }
}

