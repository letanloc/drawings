package com.dma.drawings;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawActivity extends Activity 
implements OnTouchListener,SurfaceHolder.Callback  {

	
	
	/**The SurfaceView to draw on.*/
	private  SurfaceView drawView;
	
	/** The surfaceview holder to draw to*/
	private SurfaceHolder holder;
	
	/**The background color.*/
	private int background;
	
	
	private Paint paint;
	private Path path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw);
		this.background = Color.WHITE;
		this.drawView = (SurfaceView) this.findViewById(R.string.DRAW_VIEW);
		this.drawView.setOnTouchListener(this);
		
		
		this.paint = new Paint();
		this.paint.setColor(Color.BLUE);
		this.paint.setAntiAlias(true);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeWidth(10);
	
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.path = new Path();
		this.drawView.getHolder().addCallback(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.draw, menu);
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			this.path.moveTo(event.getX(), event.getY());
		}
		else  {
			this.path.lineTo(event.getX(), event.getY());
		}
		

		Canvas c = this.holder.lockCanvas();
		if (c != null) {
			c.drawColor(this.background);
			c.drawPath(this.path, this.paint);
			this.holder.unlockCanvasAndPost(c);
		}
			
		
		
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.holder = holder;
		Canvas c = this.holder.lockCanvas();
		c.drawColor(this.background);
		holder.unlockCanvasAndPost(c);
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
