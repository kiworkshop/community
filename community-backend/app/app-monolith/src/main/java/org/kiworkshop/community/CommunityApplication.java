package org.kiworkshop.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CommunityApplication {
  public static void main(String[] args) {
    SpringApplication.run(CommunityApplication.class, args);
  }
}

