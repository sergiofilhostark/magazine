package com.summercrow.spacetip;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	
	private RelativeLayout meuLayout;
	private ImageView nave;
	private ImageView torpedo;
	private float x;
	private float y;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		meuLayout = (RelativeLayout)findViewById(R.id.layout_main);
		
		nave = new ImageView(this);
		nave.setImageResource(R.drawable.nave);
		nave.setX(0);
		nave.setY(0);
//		nave.setAlpha(0);
		meuLayout.addView(nave);
		
		torpedo = new ImageView(this);
		torpedo.setImageResource(R.drawable.torpedo);
		torpedo.setX(0);
		torpedo.setY(0);
//		torpedo.setAlpha(0);
		meuLayout.addView(torpedo);
		
		Builder dialog = new AlertDialog.Builder(this);
		
		dialog.setTitle(R.string.sua_vez);
		dialog.setMessage(R.string.posicione);
		dialog.setPositiveButton(R.string.ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_sair){
			finish();
		}
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x = (int)event.getX();
		y = (int)event.getY();
		
		int[] location = {0 , 0};
		meuLayout.getLocationOnScreen(location);
		
		y = y - location[1];
		
		System.out.println(x + " " + y + " "+ meuLayout.getY());
//		
//		nave.setX(x);
//		nave.setY(y);
		
//		Animation mira = AnimationUtils.loadAnimation(this, R.anim.mira);
//		nave.startAnimation(mira);
		
		Animation tiro = AnimationUtils.loadAnimation(this, R.anim.tiro);
//		tiro.setStartOffset(1000);
		tiro.setFillAfter(true);
		torpedo.setX(x);
		torpedo.setY(y);
		torpedo.startAnimation(tiro);
		
//		torpedo.setX(x);
//		torpedo.setY(y);
//		Animation animation = new TranslateAnimation(0, 0 ,0, 300);
//		animation.setDuration(5000);
//		animation.setInterpolator(new LinearInterpolator());
//		animation.setFillAfter(true);
//		torpedo.startAnimation(animation);
//		torpedo.setVisibility(0);
		
		return true;
	}
	
	

}
