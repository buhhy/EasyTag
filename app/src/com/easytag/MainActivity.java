package com.easytag;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;

import com.easytag.helper.GestureFilter;
import com.easytag.helper.GestureListener;

public class MainActivity extends Activity implements GestureListener {

	private GestureFilter filter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.filter = new GestureFilter(this, this);
	}
	
	public void onSwipe(int direction){
		String message = "Hello";
		Log.d("direction", "d:" + direction);
		switch(direction){
		case GestureFilter.SWIPE_UP | GestureFilter.SWIPE_LEFT:
			message = "UP LEFT";
			break;
		case GestureFilter.SWIPE_UP | GestureFilter.SWIPE_RIGHT:
			message = "UP RIGHT";
			break;
		case GestureFilter.SWIPE_DOWN | GestureFilter.SWIPE_LEFT:
			message = "DOWN LEFT";
			break;
		case GestureFilter.SWIPE_DOWN | GestureFilter.SWIPE_RIGHT:
			message = "DOWN RIGHT";
			break;
		case GestureFilter.SWIPE_UP:
			message = "UP";
			break;
		case GestureFilter.SWIPE_DOWN:
			message = "DOWN";
			break;
		case GestureFilter.SWIPE_LEFT:
			message = "LEFT";
			break;
		case GestureFilter.SWIPE_RIGHT:
			message = "RIGHT";
			break;
		}
		this.alert(message);
	}

	public void onDoubleTap(){
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		this.filter.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void alert(String message){
		Toast toast = Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT);
		toast.show();
	}

}
