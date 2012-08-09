package com.easytag.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
	
	public static final int NUM_TAG_PER_PAGE = 3;
	
	private List<Image> imageList;
	private List<Tag> tagList;
	
	private int currentImage = 0;
	private int currentTagSet = 0;
	
	public List<Image> getImageList(){
		if(this.imageList == null)
			this.imageList = new LinkedList<Image>();
		return this.imageList;
	}
	
	public List<Tag> getTagList(){
		if(this.tagList == null)
			this.tagList = new LinkedList<Tag>();
		return this.tagList;
	}
	
	public void fetchTags(){
		// TODO: add tag fetching
	}
	
	public void fetchImages(){
		// TODO: add image fetching
	}
	
	public void tagImage(int tagIndex){
		Tag tag = tagList.get(tagIndex);
		// TODO: save tag-image link using currentImage and tag.getTagId()
	}

	public Image getCurrentImage(){
		return this.getImageList().get(currentImage);
	}

	public void incrementImageIndex(){
		if(this.hasNextImage())
			this.currentImage ++;
	}
	
	public void decrementImageIndex(){
		if(this.currentImage > 0)
			this.currentImage --;
	}
	
	public boolean hasNextImage(){
		return this.currentImage == this.imageList.size() - 1;
	}

	public List<Tag> getCurrentTagSet(){
		List<Tag> list = this.getTagList();
		int start = this.currentTagSet * Model.NUM_TAG_PER_PAGE;
		int end = (this.currentTagSet + 1) * Model.NUM_TAG_PER_PAGE;
		if(end > list.size())
			end = list.size();
		
		return list.subList(
				start,
				end);
	}

	public void setCurrentTagSet(int currentTagSet){
		this.currentTagSet = currentTagSet;
	}

	public void incrementTagSetIndex(){
		if(this.hasNextTagSet())
			this.currentTagSet ++;
	}
	
	public void decrementTagSetIndex(){
		if(this.hasPreviousTagSet())
			this.currentTagSet --;
	}
	
	public boolean hasNextTagSet(){
		return this.currentTagSet * Model.NUM_TAG_PER_PAGE < this.tagList.size() - 1;
	}
	
	public boolean hasPreviousTagSet(){
		return this.currentTagSet > 0;
	}
	
}
