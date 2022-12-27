package io.cloudadc.cloud.frontend;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  public OpenAPI customOpenAPI() {
    return (new OpenAPI()).components(new Components())
      .info((new Info()).title("Backend API").description("This is Backend Application API From F5 Demo"));
  }
}
