package io.cloudadc.data;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1246300281104712602L;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private HashMap<String, String> properties ;

	public Entity(String url) {
		super();
		this.url = url;
	}

	public Entity(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@JsonCreator
	public Entity(@JsonProperty("url") String url, @JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("properties") HashMap<String, String> properties) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.properties = properties;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "Entity [url=" + url + ", username=" + username + ", password=" + password + ", properties=" + properties
		        + "]";
	}

}
