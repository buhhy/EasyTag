package com.easytag.helper;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureFilter extends SimpleOnGestureListener {
	public final static int SWIPE_UP    = 1;
	public final static int SWIPE_DOWN  = 2;
	public final static int SWIPE_LEFT  = 4;
	public final static int SWIPE_RIGHT = 8;

	public final static int MODE_TRANSPARENT = 0;
	public final static int MODE_SOLID       = 1;
	public final static int MODE_DYNAMIC     = 2;

	private final static int ACTION_FAKE = -13; //just an unlikely number
	private int swipe_Min_Distance = 100;
	private int swipe_Max_Distance = 400;
	private int swipe_Min_Velocity = 100;

	private int mode = MODE_DYNAMIC;
	private boolean running = true;
	private boolean tapIndicator = false;

	private Activity context;
	private GestureDetector detector;
	private GestureListener listener;

	public GestureFilter(Activity context, GestureListener sgl){
		this.context = context;
		this.detector = new GestureDetector(context, this);
		this.listener = sgl; 
	}

	public void onTouchEvent(MotionEvent event){
		if(!this.running)
			return;  

		boolean result = this.detector.onTouchEvent(event);
		if(this.mode == MODE_SOLID)
			event.setAction(MotionEvent.ACTION_CANCEL);
		else if(this.mode == MODE_DYNAMIC){
			Log.d("mode", event.getAction()+"v");
			if(event.getAction() == MotionEvent.ACTION_DOWN)
				listener.onHold();
			else if(event.getAction() == MotionEvent.ACTION_UP)
				listener.onRelease();
			else if(event.getAction() == MotionEvent.ACTION_MOVE)
				listener.onMove();
			
			if(event.getAction() == ACTION_FAKE)
				event.setAction(MotionEvent.ACTION_UP);
			else if(result)
				event.setAction(MotionEvent.ACTION_CANCEL);
			else if(this.tapIndicator){
				event.setAction(MotionEvent.ACTION_DOWN);
				this.tapIndicator = false;
			}
		}
		//else just do nothing, it's Transparent
	}

	public void setMode(int m){
		this.mode = m;
	}

	public int getMode(){
		return this.mode;
	}

	public void setEnabled(boolean status){
		this.running = status;
	}

	public void setSwipeMaxDistance(int distance){
		this.swipe_Max_Distance = distance;
	}

	public void setSwipeMinDistance(int distance){
		this.swipe_Min_Distance = distance;
	}

	public void setSwipeMinVelocity(int distance){
		this.swipe_Min_Velocity = distance;
	}

	public int getSwipeMaxDistance(){
		return this.swipe_Max_Distance;
	}

	public int getSwipeMinDistance(){
		return this.swipe_Min_Distance;
	}

	public int getSwipeMinVelocity(){
		return this.swipe_Min_Velocity;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
		float xDistance = Math.abs(e1.getX() - e2.getX());
		float yDistance = Math.abs(e1.getY() - e2.getY());
		int value = 0;

		if(xDistance > this.swipe_Max_Distance || yDistance > this.swipe_Max_Distance)
			return false;

		velocityX = Math.abs(velocityX);
		velocityY = Math.abs(velocityY);

		if(velocityX > this.swipe_Min_Velocity && xDistance > this.swipe_Min_Distance){
			Log.d("value", "horizontal");
			if(e1.getX() > e2.getX()) // right to left
				value |= GestureFilter.SWIPE_LEFT;
			else
				value |= GestureFilter.SWIPE_RIGHT;
		}
		if(velocityY > this.swipe_Min_Velocity && yDistance > this.swipe_Min_Distance){
			Log.d("value", "vertical");
			if(e1.getY() > e2.getY()) // bottom to up
				value |= GestureFilter.SWIPE_UP;
			else
				value |= GestureFilter.SWIPE_DOWN;
		}

		this.listener.onSwipe(value);
		
		return (value != 0);
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e){
		this.tapIndicator = true;
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent arg0){
		this.listener.onDoubleTap();;
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent arg0){
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent arg0){
		if(this.mode == MODE_DYNAMIC){        // we owe an ACTION_UP, so we fake an
			arg0.setAction(ACTION_FAKE);      //action which will be converted to an ACTION_UP later.
			this.context.dispatchTouchEvent(arg0);  
		}

		return false;
	}
}
