package io.cloudadc.cloud.frontend.fruits;

public class Fruit {
	
	  private Long id;
	  
	  private String name;
	  
	  public Fruit() {}
	  
	  public Fruit(String type) {
	    this.name = type;
	  }
	  
	  public Fruit(Long id, String name) {
	    this.id = id;
	    this.name = name;
	  }
	  
	  public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
	    return "Fruit{ name='" + this.name + '\'' + " }";
	  }
	}

