package io.cloudadc.cloud.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(io.cloudadc.cloud.frontend.Application.class, args);
  }
}

