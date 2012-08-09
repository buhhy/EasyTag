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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Model {
	
	public static final int NUM_TAG_PER_PAGE = 3;

	private List<Image> imageList;
	private List<Tag> tagList;
	
	private int currentImage = 0;
	private int currentTagSet = 0;

	private HttpController hc = new HttpController();
	private JSONHelper jh = new JSONHelper();
	public Gson gson;

	public Model() {
        gson = new Gson();
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
