package com.easytag;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easytag.helper.GestureFilter;
import com.easytag.helper.GestureListener;
import com.easytag.model.Image;
import com.easytag.model.Model;
import com.easytag.model.Tag;

public class MainActivity extends Activity implements GestureListener {

	private Model tagModel = null;
	private GestureFilter filter = null;
	private View [] dashboard = null;
	private ImageView mainImage = null;
	private LinearLayout infoBar = null;
	private LinearLayout tagBar = null;
	private TextView[] tagTexts = null;
	
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
			if(GestureFilter.CHECK_DIRECTION(direction, GestureFilter.SWIPE_DOWN)){
				model.nextImage();
				this.rerenderImage();
			}
			else if(GestureFilter.CHECK_DIRECTION(direction, GestureFilter.SWIPE_UP)){
				model.tagImage(x);
				model.nextImage();
				this.rerenderImage();
			}
			else {
				if(x == 0)
					model.previousTagSet();
				else
					model.nextTagSet();
				this.rerenderTags();
			}
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
		
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		
		Model model = this.getModel();
		model.fetchTags();
//		model.fetchImages();

		this.dashboard = new View[3];
		this.tagTexts = new TextView[3];
		this.mainImage = (ImageView)this.findViewById(R.id.image);
		this.infoBar = (LinearLayout) this.findViewById(R.id.infoBar);
		this.tagBar = (LinearLayout) this.findViewById(R.id.tagBar1);

		this.dashboard[0] = this.findViewById(R.id.skipButton);
		this.dashboard[1] = this.findViewById(R.id.previousSet);
		this.dashboard[2] = this.findViewById(R.id.nextSet);
		
		this.tagTexts[0] = (TextView) this.findViewById(R.id.tag11);
		this.tagTexts[1] = (TextView) this.findViewById(R.id.tag12);
		this.tagTexts[2] = (TextView) this.findViewById(R.id.tag13);
		
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
	
	public void onUpdateTags(){
		this.rerenderTags();
	}
	
	public void onUpdateImage(){
		this.rerenderImage();
	}
	
	public void rerenderImage(){
		Model model = this.getModel();
		Image currentImage = model.getCurrentImage();
		Log.d("image", model.getCurrentImage().toString());
		
		Drawable drawable = this.LoadImageFromWebOperations(currentImage.getImageUrl());
		if(drawable != null)
			this.mainImage.setImageDrawable(drawable);
	}
	
	public void rerenderTags(){
		Model model = this.getModel();
		List<Tag> currentTags = model.getCurrentTagSet();
		Log.d("tags", model.getCurrentTagSet().toString());
		
		for(int i = 0; i < this.tagTexts.length; i++){
			if(i < currentTags.size())
				tagTexts[i].setText(currentTags.get(i).getName());
			else
				tagTexts[i].setText("");
		}
	}

	public Model getModel(){
		if(this.tagModel == null)
			this.tagModel = new Model(this);
		return this.tagModel;
	}

	public void showDashboard(){
		for(View view : this.dashboard){
			view.setVisibility(View.VISIBLE);
		}
	}

	public void hideDashboard(){
		for(View view : this.dashboard){
			view.setVisibility(View.INVISIBLE);
		}
	}

//	public void alert(String message){
//		Toast toast = Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT);
//		toast.show();
//	}
//
//	public void highlight(int x, int y){
//		TextView view = ((TextView) this.boxView[y][x]);
//		view.setShadowLayer(10.0f, 0.0f, 0.0f, 0xffff0000);
//	}
	
	private Drawable LoadImageFromWebOperations(String url){
 		try {
 			InputStream is = (InputStream) new URL(url).getContent();
 			Drawable d = Drawable.createFromStream(is, "src name");
 			return d;
 		}
 		catch(Exception e){
 			e.printStackTrace();
 			return null;
 		}
 	}

}
