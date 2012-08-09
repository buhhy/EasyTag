package com.easytag.helper;

import android.view.MotionEvent;

public interface GestureListener {
	public void onDoubleTap();
	public void onHold(MotionEvent me);
	public void onRelease(MotionEvent me);
	public void onSwipe(int direction);
	public void onMove(MotionEvent event);
}
