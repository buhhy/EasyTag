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
		list.add(new Image(1, "http://www.formorf.com/adad/wordpress/wp-content/uploads/2010/07/cell-mutation1.jpg"));
		list.add(new Image(2, "http://fc01.deviantart.net/fs4/i/2004/230/2/f/Simple_Cell_Mutation.jpg"));
		list.add(new Image(3, "http://www.1080p-wallpapers.com/images/3D-wallpapers/blue-neon-mushrooms-3d-image.jpg"));
		list.add(new Image(4, "http://www.freegreatpicture.com/files/166/22526-bing-bing-wallpaper.jpg"));
		list.add(new Image(5, "http://hssn-media.advance.net/OregonLive.com/news/f156376d356bbd9a3f14fa0d19d44371/bing.JPG"));
		list.add(new Image(6, "http://farm3.staticflickr.com/2722/4426791707_05059d991e.jpg"));
		list.add(new Image(7, "http://i2.cdn.turner.com/si/2012/olympics/2012/writers/sl_price/08/07/Liu-Xiang/Liu-Xiang-1.jpg"));
		list.add(new Image(8, "http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2012/8/7/1344334848032/Liu-Xiang--008.jpg"));
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
