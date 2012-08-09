package com.easytag.model;

public class Tag {
	
	private int id;
	private String name;
	private String created_at;
	private String updated_at;
	
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

	public void setCreated_at(String created_at){
		this.created_at = created_at;
	}
	public String getCreated_at(){
		return this.created_at;
	}
	
	public void setUpdated_at(String updated_at){
		this.updated_at = updated_at;
	}
	public String getUpdated_at(){
		return this.updated_at;
	}
}
