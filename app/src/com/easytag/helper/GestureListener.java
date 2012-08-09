package com.easytag.helper;

import android.view.MotionEvent;

public interface GestureListener {
	public void onDoubleTap();
	public void onHold();
	public void onRelease();
	public void onSwipe(int direction);
	public void onMove(MotionEvent event);
}
