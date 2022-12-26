package io.cloudadc.cloud.fruits.controller;


import io.cloudadc.cloud.fruits.controller.Fruit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/fruits", produces = { "application/json"})
@Tag(name = "Fruit", description = "The Fruit API")
public class FruitController {
	
  
	@Autowired
	private FruitsRepository db;
	
	@RequestMapping(path = {"/"}, method = {RequestMethod.GET})
	@ResponseBody
	@Operation(summary = "Get all fruits", description = "Return all fruits in repositories")
	public List<Fruit> getAll() {
		return db.getAll();
	}
  
	@RequestMapping(path = {"/{id}"}, method = {RequestMethod.GET})
	@ResponseBody
	@Operation(summary = "Get fruit by id", description = "Returns fruit for id specified.")
	public Fruit getFruit(@Parameter(description = "Fruit id", required = true) @PathVariable("id") Long id) {
		return db.get(id);
	}
	
	@RequestMapping(path = {"/"}, method = {RequestMethod.POST})
	@Operation(summary = "Add fruit", description = "Add a fruit to repository")
	public void addFruit(@io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody Fruit fruit) {
		db.add(fruit);
	}
	
	@RequestMapping(path = {"/{id}"}, method = {RequestMethod.DELETE})
	@ResponseBody
	@Operation(summary = "delete fruit by id", description = "Delete fruit for id specified.")
	public Boolean deleteFruit(@Parameter(description = "Fruit id", required = true) @PathVariable("id") Long id) {
		return db.delete(id);
	}
	
	@RequestMapping(path = {"/"}, method = {RequestMethod.PATCH})
	@Operation(summary = "Update fruit", description = "Update exit fruit in repository")
	public void updateFruit(@io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody Fruit fruit) {
		db.update(fruit);
	}
}