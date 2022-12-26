package io.cloudadc.cloud.fruits.controller;

import java.io.Serializable;

public class Fruit implements Serializable {
	

	private static final long serialVersionUID = 5177291772896683156L;

	private Long id;
	  
	  private String name;
	  
	  private String color;
	  
	  private Double weight;
	  
	  public Fruit() {}
	  
	  public Fruit(String type) {
	    this.name = type;
	  }
	  
	  public Fruit(Long id, String name) {
		    this.id = id;
		    this.name = name;
		  }
	  
	  public Fruit(Long id, String name, String color, Double weight) {
	    this.id = id;
	    this.name = name;
	    this.color = color;
	    this.weight = weight;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Fruit [id=" + id + ", name=" + name + ", color=" + color + ", weight=" + weight + "]";
	}

	
}

