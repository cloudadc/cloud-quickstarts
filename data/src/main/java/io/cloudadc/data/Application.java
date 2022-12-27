package io.cloudadc.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(io.cloudadc.data.Application.class, args);
  }
}

