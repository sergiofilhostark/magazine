package com.summercrow.spacetip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Campo extends View{
	
	private Drawable nave1;
	private int x;
	private int y;

	public Campo(Context context) {
		super(context);
		init(context);
	}
	
	public Campo(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context);
	}
	
	private void init(Context context) {
		setBackgroundResource(R.drawable.espaco);
		nave1 = context.getResources().getDrawable(R.drawable.nave);
		setFocusable(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		nave1.setBounds(x, y, x + 100, y + 100);
		nave1.draw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x = (int)event.getX();
		y = (int)event.getY();
		
		invalidate();
		
		return true;
	}

}
