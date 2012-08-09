package com.easytag;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

	public void onSwipe(int direction){
		int x = 1;
//		int y = 1;

		Model model = this.getModel();

		if(GestureFilter.CHECK_DIRECTION(direction, GestureFilter.SWIPE_LEFT))
			x = 0;
		else if(GestureFilter.CHECK_DIRECTION(direction, GestureFilter.SWIPE_RIGHT))
			x = 2;

//		if(GestureFilter.CHECK_DIRECTION(direction, GestureFilter.SWIPE_UP))
//			y = 0;
//		else if(GestureFilter.CHECK_DIRECTION(direction, GestureFilter.SWIPE_DOWN))
//			y = 2;

		if(direction != 0){
			if(GestureFilter.CHECK_DIRECTION(direction, GestureFilter.SWIPE_DOWN))
				model.nextImage();
			else if(GestureFilter.CHECK_DIRECTION(direction, GestureFilter.SWIPE_UP))
				model.tagImage(x);
			else {
				if(x == 0)
					model.previousTagSet();
				else
					model.nextTagSet();
			}
			this.rerender();
		}
	}

	public void onHold(){
		this.showDashboard();
	}

	public void onRelease(){
		this.hideDashboard();
	}

	public void onMove(){
	}

	public void onDoubleTap(){
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Model model = this.getModel();
		model.fetchImages();
		model.fetchTags();

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
	
	public void rerender(){
		Model model = this.getModel();
		Log.d("tags", model.getCurrentTagSet().toString());
		Log.d("image", model.getCurrentImage().toString());
	}

	public Model getModel(){
		if(this.tagModel == null)
			this.tagModel = new Model();
		return this.tagModel;
	}

	public void showDashboard(){

	}

	public void hideDashboard(){

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
