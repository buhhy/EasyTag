package com.easytag.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.util.Log;

import com.easytag.HttpController;
import com.easytag.helper.AsyncCallback;
import com.easytag.helper.JSONHelper;

public class Model {
	
	public static final int NUM_TAG_PER_PAGE = 3;

	private List<Image> imageList;
	private List<Tag> tagList;
	
	private int currentImageIndex = 0;
	private int currentTagSetIndex = 0;

	private HttpController hc = new HttpController();
	private JSONHelper jh = new JSONHelper();
	public Gson gson;

	public Model(){
        this.gson = new Gson();
	}
	
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

	public void fetchTags() {
//		List<Tag> list = this.getTagList();
//		list.add(new Tag(1, "1"));
//		list.add(new Tag(2, "2"));
//		list.add(new Tag(3, "3"));
//		list.add(new Tag(4, "4"));
//		list.add(new Tag(5, "5"));
//		list.add(new Tag(6, "6"));
//		list.add(new Tag(7, "7"));
		
		String result = "";
		try {
			hc.caller = (new AsyncCallback() {
				   public void call(String result) {
					   Log.v("test", "]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]] i hate my life 111111 ");
					   tagList = gson.fromJson(result, List.class);
					   for (Tag t : tagList) {
						   Log.v("test", "]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]] " + t.getName());
					   }
					   //{"created_at":"2012-08-09T07:51:19Z","id":1,"name":"Awesome","updated_at":"2012-08-09T07:51:19Z"}
				   }
				});
			hc.execute(new URL("http://sleepy-cove-3041.herokuapp.com/tags.json")); //Need to know the correct GET request
			Log.v("test", "=============================" + result);
		} catch (MalformedURLException exception) {
			Log.e("test", exception.toString());
		}
		
		//Need to know how tagged results are returned
	}
	
	public void fetchImages(){
//		List<Image> list = this.getImageList();
//		list.add(new Image(1, "http://www.formorf.com/adad/wordpress/wp-content/uploads/2010/07/cell-mutation1.jpg"));
//		list.add(new Image(2, "http://fc01.deviantart.net/fs4/i/2004/230/2/f/Simple_Cell_Mutation.jpg"));
//		list.add(new Image(3, "http://www.1080p-wallpapers.com/images/3D-wallpapers/blue-neon-mushrooms-3d-image.jpg"));
//		list.add(new Image(4, "http://www.freegreatpicture.com/files/166/22526-bing-bing-wallpaper.jpg"));
//		list.add(new Image(5, "http://hssn-media.advance.net/OregonLive.com/news/f156376d356bbd9a3f14fa0d19d44371/bing.JPG"));
//		list.add(new Image(6, "http://farm3.staticflickr.com/2722/4426791707_05059d991e.jpg"));
//		list.add(new Image(7, "http://i2.cdn.turner.com/si/2012/olympics/2012/writers/sl_price/08/07/Liu-Xiang/Liu-Xiang-1.jpg"));
//		list.add(new Image(8, "http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2012/8/7/1344334848032/Liu-Xiang--008.jpg"));
		
		String result = "";
		try {
			hc.execute(new URL("http://sleepy-cove-3041.herokuapp.com/content-tagss/tag/2/content/3.json"));;
			result = hc.get();
		} catch (MalformedURLException exception) {
			Log.e("test", exception.toString());
		} catch (InterruptedException exception) {
			Log.e("test", exception.toString());
		} catch (ExecutionException exception) {
			Log.e("test", exception.toString());
		}
	}
	
	public void processImages(String result) {
	}
	
	public void tagImage(int tagIndex){
		Tag tag = tagList.get(this.currentTagSetIndex * Model.NUM_TAG_PER_PAGE + tagIndex);
		Log.d("save", tag.getName() + ": " + this.getCurrentImage().getImageUrl());
		// TODO: save tag-image link using currentImage and tag.getTagId()
	}

	public Image getCurrentImage(){
		return this.getImageList().get(currentImageIndex);
	}

	public void nextImage(){
		if(this.hasNextImage())
			this.currentImageIndex ++;
	}
	
	public void previousImage(){
		if(this.currentImageIndex > 0)
			this.currentImageIndex --;
	}
	
	public boolean hasNextImage(){
		return (this.currentImageIndex < this.imageList.size() - 1);
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
