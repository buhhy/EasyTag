package com.easytag;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easytag.helper.GestureFilter;
import com.easytag.helper.GestureListener;
import com.easytag.model.Model;

public class MainActivity extends Activity implements GestureListener {

	private Model tagModel = null;
	private GestureFilter filter = null;
	private View [][] boxView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.tagModel = new Model();
		this.tagModel.fetchTags();
		this.boxView = new View[3][3];
		
		this.boxView[0][0] = this.findViewById(R.id.box00);
		this.boxView[0][1] = this.findViewById(R.id.box01);
		this.boxView[0][2] = this.findViewById(R.id.box02);
		this.boxView[1][0] = this.findViewById(R.id.box10);
		this.boxView[1][1] = this.findViewById(R.id.box11);
		this.boxView[1][2] = this.findViewById(R.id.box12);
		this.boxView[2][0] = this.findViewById(R.id.box20);
		this.boxView[2][1] = this.findViewById(R.id.box21);
		this.boxView[2][2] = this.findViewById(R.id.box22);
		
		this.filter = new GestureFilter(this, this);
	}
	
	public void onSwipe(int direction){
		int x = 1;
		int y = 1;

		if((GestureFilter.SWIPE_LEFT & direction) != 0)
			x = 0;
		else if((GestureFilter.SWIPE_RIGHT & direction) != 0)
			x = 2;
		
		if((GestureFilter.SWIPE_UP & direction) != 0)
			y = 0;
		else if((GestureFilter.SWIPE_DOWN & direction) != 0)
			y = 2;
		
		if(x != 1 || y != 1)
			this.highlight(x, y);
		
//		String message = "Hello";
//		Log.d("direction", "d:" + direction);
//		switch(direction){
//		case GestureFilter.SWIPE_UP | GestureFilter.SWIPE_LEFT:
//			message = "UP LEFT";
//			break;
//		case GestureFilter.SWIPE_UP | GestureFilter.SWIPE_RIGHT:
//			message = "UP RIGHT"; 
//			break;
//		case GestureFilter.SWIPE_DOWN | GestureFilter.SWIPE_LEFT:
//			message = "DOWN LEFT";
//			break;
//		case GestureFilter.SWIPE_DOWN | GestureFilter.SWIPE_RIGHT:
//			message = "DOWN RIGHT";
//			break;
//		case GestureFilter.SWIPE_UP:
//			message = "UP";
//			break;
//		case GestureFilter.SWIPE_DOWN:
//			message = "DOWN";
//			break;
//		case GestureFilter.SWIPE_LEFT:
//			message = "LEFT";
//			break;
//		case GestureFilter.SWIPE_RIGHT:
//			message = "RIGHT";
//			break;
//		}
//		this.alert(message);
	}

	public void onHold(){
	}

	public void onRelease(){
	}

	public void onMove(){
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
	
	public void highlight(int x, int y){
		TextView view = ((TextView) this.boxView[y][x]);
		view.setShadowLayer(10.0f, 0.0f, 0.0f, 0xffff0000);
	}
	
	

}
