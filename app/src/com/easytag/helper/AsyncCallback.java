package com.easytag.helper;

public abstract class AsyncCallback {
	protected Object model;
	
	public AsyncCallback(Object model){
		this.model = model;
	}
	
	public abstract Object call(String result);
} 