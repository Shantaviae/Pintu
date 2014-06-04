package com.tangibledesign.pintu;

import android.view.View;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
//import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.graphics.PorterDuff;


public class DrawingView extends View {
	
	//drawing path
	private Path drawPath;
	//drawing and canvas paint
	private Paint drawPaint, canvasPaint;
	//initial color
	private int paintColor = 0xFF000000;
	//canvas
	private Canvas drawCanvas;
	//canvas bitmap
	private Bitmap canvasBitmap;
	
	private boolean erase=false;
	
	private boolean draw =true;
	
	public DrawingView(Context context, AttributeSet attrs){
	    super(context, attrs);
	    setupDrawing();
	}
	
	public void setErase(boolean isErase){
		//set erase true or false
		erase=isErase;
		
		//alter the Paint object to erase or switch back to drawing:
		if(erase) {
			//drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			drawPaint.setColor(Color.WHITE);
			drawPaint.setStrokeWidth(40);
		} else { 
			//drawPaint.setXfermode(null);
			drawPaint.setColor(Color.BLACK);
			drawPaint.setStrokeWidth(10);
		}
	}
	
	public void setDraw(boolean isDraw){
		//set erase true or false
		draw=isDraw;
		
		//alter the Paint object to erase or switch back to drawing:
		if(draw) {
			//drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			drawPaint.setColor(Color.BLACK);
			drawPaint.setStrokeWidth(10);
		} else { 
			//drawPaint.setXfermode(null);
			drawPaint.setColor(Color.BLACK);
			drawPaint.setStrokeWidth(0);
		}
	}
	
	@SuppressLint("DefaultLocale") public String toString() {
        return String.format("DrawingView[paintColor: %d, drawCanvas: %B]", paintColor, drawCanvas!=null);
    }
	
	private void setupDrawing(){
		//get drawing area setup for interaction        
		
		drawPath = new Path();
		drawPaint = new Paint();
		
		drawPaint.setColor(Color.BLACK);
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(10);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		
		canvasPaint = new Paint(Paint.DITHER_FLAG);
	}

	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	
		//view given size
		super.onSizeChanged(w, h, oldw, oldh);
		
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
	
		//draw view
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		//detect user touch 
		float touchX = event.getX();
		float touchY = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		    drawPath.moveTo(touchX, touchY);
		    break;
		case MotionEvent.ACTION_MOVE:
		    drawPath.lineTo(touchX, touchY);
		    break;
		case MotionEvent.ACTION_UP:
		    drawCanvas.drawPath(drawPath, drawPaint);
		    drawPath.reset();
		    break;
		default:
		    return false;
		}
		
		invalidate();
		return true;
	}
	
	public void startNew(){
	    drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
	    invalidate();
	    
	}	
}
