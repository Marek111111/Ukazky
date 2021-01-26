

package com.marek.nextplayer.Core;


public class Variable {
	
	private String name;
	
	private String value;
	
	public Variable(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public void set(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isTrue() {
		return value.equals("true");
	}

}
