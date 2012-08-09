package com.easytag.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.util.Log;

import com.easytag.HttpController;
import com.easytag.MainActivity;
import com.easytag.helper.AsyncCallback;
import com.easytag.helper.JSONHelper;
import com.google.gson.Gson;
import com.google.gson.internal.StringMap;

public class Model {

	public static final int NUM_TAG_PER_PAGE = 3;

	private List<Image> imageList;
	private List<Tag> tagList;

	private int currentImageIndex = 0;
	private int currentTagSetIndex = 0;

	private JSONHelper jh = null;
	private Gson gson = null;
	private MainActivity listener = null;

	public Model(MainActivity listener){
		this.gson = new Gson();
		this.listener = listener;
		this.jh = new JSONHelper();
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
	
	public void addTag(Tag tag){
		this.getTagList().add(tag);
	}
	public void addImage(Image img){
		this.getImageList().add(img);
	}
	
	public void fetchTags() {
		try {
			HttpController hc = new HttpController(listener);
			hc.caller = (new AsyncCallback(this) {
				public Object call(String result) {
					Model model = (Model) this.model;
					List<StringMap<Object>> mapList = gson.fromJson(result, List.class);
					for(StringMap<Object> s : mapList)
						model.addTag(new Tag(((Double) s.get("id")).intValue(), s.get("name").toString()));
					return Tag.class;
				}
			});
			hc.execute(new URL("http://sleepy-cove-3041.herokuapp.com/tags.json")); //Need to know the correct GET request
		} catch (MalformedURLException exception) {
			Log.e("test", exception.toString());
		}
	}

	public void fetchImages(){
		try {
			HttpController hc = new HttpController(listener);
			hc = new HttpController(this.listener); 
			hc.caller = (new AsyncCallback(this) {
				public Object call(String result) {
					Model model = (Model) this.model;
					List<StringMap<Object>> mapList = gson.fromJson(result, List.class);
					for(StringMap<Object> s : mapList)
						model.addImage(new Image(((Double) s.get("id")).intValue(), s.get("img_path").toString()));
					return Image.class;
				}
			});
			hc.execute(new URL("http://sleepy-cove-3041.herokuapp.com/contents.json"));
		} catch (MalformedURLException exception) {
			Log.e("test", exception.toString());
		}
	}
	
	public void tagImage(int contentId){
		try {
			HttpController hc = new HttpController(listener);
			hc.caller = (new AsyncCallback(this) {
				public Object call(String result) {
					Model model = (Model) this.model;
					List<StringMap<Object>> mapList = gson.fromJson(result, List.class);
					for(StringMap<Object> s : mapList)
						model.addImage(new Image(((Double) s.get("id")).intValue(), s.get("img_path").toString()));
					return Tag.class;
				}
			});
			int tagIndex = this.currentTagSetIndex * Model.NUM_TAG_PER_PAGE + contentId;
			hc.execute(new URL("http://sleepy-cove-3041.herokuapp.com/tagPhoto/" + tagIndex + "/" + this.currentImageIndex));
			Tag tag = tagList.get(tagIndex);
			Log.d("save", tag.getName() + ": " + this.getCurrentImage().getImageUrl());
		} catch (MalformedURLException exception) {
			Log.e("test", exception.toString());
		}
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
