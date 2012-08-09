package com.easytag.model;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;

public class Model {
	
	public static final int NUM_TAG_PER_PAGE = 3;
	
	private List<Image> imageList;
	private List<Tag> tagList;
	
	private int currentIndexIndex = 0;
	private int currentTagSetIndex = 0;
	
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
		List<Tag> list = this.getTagList();
		list.add(new Tag(1, "1"));
		list.add(new Tag(2, "2"));
		list.add(new Tag(3, "3"));
		list.add(new Tag(4, "4"));
		list.add(new Tag(5, "5"));
		list.add(new Tag(6, "6"));
		list.add(new Tag(7, "7"));
		// TODO: add tag fetching
	}
	
	public void fetchImages(){
		List<Image> list = this.getImageList();
		list.add(new Image(1, "a"));
		list.add(new Image(2, "b"));
		list.add(new Image(3, "c"));
		list.add(new Image(4, "d"));
		list.add(new Image(5, "e"));
		list.add(new Image(6, "f"));
		list.add(new Image(7, "g"));
		list.add(new Image(8, "h"));
		// TODO: add image fetching
	}
	
	public void tagImage(int tagIndex){
		Tag tag = tagList.get(this.currentTagSetIndex * Model.NUM_TAG_PER_PAGE + tagIndex);
		Log.d("save", tag.getTagName() + ": " + this.getCurrentImage().getImageUrl());
		// TODO: save tag-image link using currentImage and tag.getTagId()
	}

	public Image getCurrentImage(){
		return this.getImageList().get(currentIndexIndex);
	}

	public void nextImage(){
		if(this.hasNextImage())
			this.currentIndexIndex ++;
	}
	
	public void previousImage(){
		if(this.currentIndexIndex > 0)
			this.currentIndexIndex --;
	}
	
	public boolean hasNextImage(){
		return (this.currentIndexIndex < this.imageList.size() - 1);
	}

	public List<Tag> getCurrentTagSet(){
		List<Tag> list = this.getTagList();
		int start = this.currentTagSetIndex * Model.NUM_TAG_PER_PAGE;
		int end = (this.currentTagSetIndex + 1) * Model.NUM_TAG_PER_PAGE;
		if(end > list.size())
			end = list.size();
		
		return list.subList(start, end);
	}

	public void nextTagSet(){
		if(this.hasNextTagSet())
			this.currentTagSetIndex ++;
	}
	
	public void previousTagSet(){
		if(this.hasPreviousTagSet())
			this.currentTagSetIndex --;
	}
	
	public boolean hasNextTagSet(){
		return (this.currentTagSetIndex * Model.NUM_TAG_PER_PAGE < this.tagList.size() - 1);
	}
	
	public boolean hasPreviousTagSet(){
		return this.currentTagSetIndex > 0;
	}
	
}
