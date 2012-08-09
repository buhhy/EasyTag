package com.easytag.model;

public class Image {

	private int imageId;
	private String imageUrl;
	
	public Image(int imageId, String imageUrl){
		this.imageId = imageId;
		this.imageUrl = imageUrl;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, url: %s", this.imageId, this.imageUrl);
	}
	
	public int getImageId(){
		return imageId;
	}
	
	public void setImageId(int imageId){
		this.imageId = imageId;
	}
	
	public String getImageUrl(){
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}
	
}
