package com.easytag.helper;

public interface GestureListener {
	public void onDoubleTap();
	public void onHold();
	public void onRelease();
	public void onSwipe(int direction);
	public void onMove();
}
