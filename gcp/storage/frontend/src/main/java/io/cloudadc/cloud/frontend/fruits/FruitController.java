package io.cloudadc.cloud.frontend.fruits;


import io.cloudadc.cloud.frontend.fruits.Fruit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = {"text/plain", "application/*"})
@Tag(name = "Fruit", description = "The fruit API")
public class FruitController {
	
  static List<Fruit> repository = new ArrayList<>();
  
  static {
    repository.add(new Fruit(Long.valueOf(1L), "Cherry"));
    repository.add(new Fruit(Long.valueOf(2L), "Apple"));
    repository.add(new Fruit(Long.valueOf(3L), "Banana"));
  }
  
  @RequestMapping(path = {"/fruits"}, method = {RequestMethod.GET}, produces = {"text/plain", "application/*"})
  @ResponseBody
  @Operation(summary = "Get all fruits", description = "Return all fruits in repositories")
  public List<Fruit> getAll() {
	System.out.println("/fruits: " + repository);
    return repository;
  }
  
  @RequestMapping(path = {"/fruits/{id}"}, method = {RequestMethod.GET})
  @ResponseBody
  @Operation(summary = "Get fruit by id", description = "Returns fruit for id specified.")
  public Fruit getFruit(@Parameter(description = "Fruit id", required = true) @PathVariable("id") Long id) {
    for (int i = 0; i < repository.size(); i++) {
      if (((Fruit)repository.get(i)).getId() == id)
        return repository.get(i); 
    } 
    return new Fruit("New");
  }
}