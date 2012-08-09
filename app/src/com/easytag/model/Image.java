package com.easytag.model;

public class Image {

	private int id;
	private String img_path;
	
	public Image(int imageId, String imageUrl){
		this.id = imageId;
		this.img_path = imageUrl;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, url: %s", this.id, this.img_path);
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int imageId){
		this.id = imageId;
	}
	
	public String getImageUrl(){
		return img_path;
	}
	
	public void setImageUrl(String imageUrl){
		this.img_path = imageUrl;
	}
	
}
