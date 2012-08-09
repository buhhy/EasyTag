package com.easytag.model;

public class Tag {
	
	private int tagId;
	private String tagName;
	
	public Tag(int tagId, String tagName){
		this.tagId = tagId;
		this.tagName = tagName;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, name: %s", this.tagId, this.tagName);
	}
	
	public int getTagId(){
		return tagId;
	}
	
	public void setTagId(int tagId){
		this.tagId = tagId;
	}
	
	public String getTagName(){
		return tagName;
	}
	
	public void setTagName(String tagName){
		this.tagName = tagName;
	}

}
