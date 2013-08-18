package com.summercrow.spacetip.cliente;

import com.summercrow.spacetip.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Fundo extends View{
	
	private Paint vermelho;

	public Fundo(Context context) {
		super(context);
		init(context);
	}
	
	public Fundo(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context);
	}
	
	private void init(Context context) {
		
		vermelho = new Paint();
		vermelho.setARGB(255, 255, 0, 0);
		
		setBackgroundResource(R.drawable.espaco);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
		
		
		float y = getHeight() / 2;
		float x = getWidth();
		
		canvas.drawLine(0, y, x, y, vermelho);
	}
	
	

}
