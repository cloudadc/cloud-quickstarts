package io.cloudadc.cloud.fruits.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FruitsRepository {
	
	 static List<Fruit> repository = Collections.synchronizedList(new ArrayList<>());
	   
	 static {
	    repository.add(new Fruit(Long.valueOf(1000001L), "Cherry", "purple", Double.valueOf(1.1)));
	    repository.add(new Fruit(Long.valueOf(1000002L), "Apple", "red", Double.valueOf(2.2)));
	    repository.add(new Fruit(Long.valueOf(1000003L), "Banana", "yellow", Double.valueOf(3.3)));
	  }
	 
	 public List<Fruit> getAll() {
		 return repository;
	 }
	 
	 public Fruit get(Long id) {
		 for (int i = 0; i < repository.size(); i++) {
		      if ((repository.get(i)).getId().equals(id))
		        return repository.get(i); 
		 } 
		 return new Fruit(Long.valueOf(1000000L), "NONE");
	 }
	 
	 public void add(Fruit fruit) {
		 update(fruit);
	 }
	 
	 public Boolean delete(Long id) {
		 Fruit f = get(id);
		 if(f.getName().equals("NONE")) {
			 return false;
		 } else {
			 repository.remove(f);
			 return true;
		 }
	 }
	 
	 public void update(Fruit fruit) {
		 Fruit f = get(fruit.getId());
		 if(f.getName().equals("NONE")) {
			 repository.add(fruit);
		 } else {
			 f.setColor(fruit.getColor());
			 f.setName(fruit.getName());
			 f.setWeight(fruit.getWeight());
		 }
	 }

}
