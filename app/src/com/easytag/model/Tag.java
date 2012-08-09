package com.easytag.model;

public class Tag {
	
	private int id;
	private String name;
	private String created_at;
	private String updated_at;
	
	//{"created_at":"2012-08-09T07:51:19Z","id":1,"name":"Awesome","updated_at":"2012-08-09T07:51:19Z"}
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
