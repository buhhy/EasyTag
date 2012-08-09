package com.easytag.model;

public class Tag {
	
	private int id;
	private String name;
	
	public Tag(int id, String tagName){
		this.id = id;
		this.name = tagName;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, name: %s", this.id, this.name);
	}
	
	public int getId(){
		return id;
	}
	public void setId(int tagId){
		this.id = tagId;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String tagName){
		this.name = tagName;
	}
	
}
