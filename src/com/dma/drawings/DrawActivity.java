package com.dma.drawings;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawActivity extends Activity 
implements OnTouchListener,SurfaceHolder.Callback, Runnable  {

	
	
	/**The SurfaceView to draw on.*/
	private  SurfaceView drawView;
	
	/** The surfaceview holder to draw to*/
	private SurfaceHolder holder;
	
	/**The background color.*/
	private int background;
	
	/**A thread use for drawing.*/
	
	private Paint paint;
	private Path path;
	private ArrayList<Path> arrayOfPaths;
	private ArrayList<Paint> arrayOfPaints;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_draw); 
		this.background = Color.WHITE;
		this.drawView = (SurfaceView) this.findViewById(R.string.DRAW_VIEW);
		this.drawView.setOnTouchListener(this);

		this.paint = new Paint();
		this.paint.setColor(Color.RED);
		this.paint.setAntiAlias(true);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeWidth(10);
		this.path = new Path();
		this.arrayOfPaths = new ArrayList<Path>();
		this.arrayOfPaints = new ArrayList<Paint>();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.drawView.getHolder().addCallback(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.draw, menu);
		return true;
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int c = Color.TRANSPARENT;
	   if (item.getItemId() == R.id.Red) {
		   c = Color.RED;
	   }
	   else if (item.getItemId() == R.id.Green) {
		   c = Color.GREEN;
	   }
	   else if (item.getItemId() == R.id.blue) {
		   c = Color.BLUE;
	   }
	   
	   
	   if (c != this.paint.getColor()) {
		   this.arrayOfPaths.add(this.path);
		   this.path = new Path();
		   this.arrayOfPaints.add(new Paint(this.paint));
		   this.paint.setColor(c);
	   }	
	   return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			this.path.moveTo(event.getX(), event.getY());
		}
		else  {
			this.path.lineTo(event.getX(), event.getY());
		}
		new Thread(this).start();
		
			
		
		
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

	@Override
	public void run() {
		Canvas c = this.holder.lockCanvas();
		if (c != null) {
			c.drawColor(this.background);
			
			for (int i = 0; i < this.arrayOfPaints.size(); i++) {
				c.drawPath(this.arrayOfPaths.get(i), this.arrayOfPaints.get(i));
			}
			
			c.drawPath(this.path, this.paint);
			this.holder.unlockCanvasAndPost(c);
		}
		
	}

}
