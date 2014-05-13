package com.tangibledesign.pintu;

import java.util.Locale;
import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;

import java.util.concurrent.TimeUnit;  

import android.annotation.SuppressLint;  
import android.annotation.TargetApi;  
import android.os.Build; 
import android.graphics.Typeface;

public class MediumActivity extends ActionBarActivity {

	//custom drawing view
	public DrawingView drawView;
	TextView textViewTime;
	TextView textViewScore;
	public int score = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medium);
		
		Typeface font = Typeface.createFromAsset( getAssets(), "FontAwesome.otf" );
		Button btn_clear = (Button)findViewById( R.id.new_btn );
		Button btn_save = (Button)findViewById( R.id.submit_btn );
		btn_clear.setTypeface(font);
		btn_save.setTypeface(font);
		
		drawView = (DrawingView) findViewById(R.id.drawing);
		textViewTime = (TextView)findViewById(R.id.timer);  
		textViewScore = (TextView)findViewById(R.id.score); 
        textViewTime.setText(" 2:01"); 
        textViewScore.setText(" 0");
        final CounterClass timer = new CounterClass(121000,1000); 
        timer.start();
	}

	public void clearScreen(View view){
		AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
		newDialog.setTitle("Clear Attempt");
		newDialog.setMessage("Start new character?\nNOTE: You will lose the current attempt.");
		newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		        drawView.startNew();
		        dialog.dismiss();
		    }
		});
		newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		        dialog.cancel();
		    }
		});
		newDialog.show();
	}

	public void addScore (){
		score = score + 100;
		String scoreText = String.format(Locale.US," %d", score); 
		textViewScore.setText(scoreText); 
	}
	public void submitEntry(View view){
		
		AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
		saveDialog.setTitle("Submit");
		saveDialog.setMessage("Submit character?");
		saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		        //save drawing
		    	drawView.setDrawingCacheEnabled(true);
				String imgSaved = MediaStore.Images.Media.insertImage(
					    getContentResolver(), drawView.getDrawingCache(),
					    UUID.randomUUID().toString()+".png", "drawing");
				if(imgSaved!=null){
				    Toast savedToast = Toast.makeText(getApplicationContext(), 
				        "Correct!", Toast.LENGTH_SHORT);
				    addScore();
				    savedToast.show();
				    drawView.startNew();
				    drawView.destroyDrawingCache();
				}
				else{
				    Toast unsavedToast = Toast.makeText(getApplicationContext(), 
				        "Incorrect! Try again!", Toast.LENGTH_SHORT);
				    unsavedToast.show();
				}
		    }
		});
		saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		        dialog.cancel();
		    }
		});
		saveDialog.show();
	}
	
	
	protected void alertbox(String title, String mymessage)
	   {
	   new AlertDialog.Builder(this)
	      .setMessage(mymessage)
	      .setTitle(title)
	      .setCancelable(true)
	      .setNeutralButton(android.R.string.cancel,
	         new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton){}
	         })
	      .show();
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medium, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD) 
	@SuppressLint("NewApi") 
	public class CounterClass extends CountDownTimer { 
		public CounterClass(long millisInFuture, long countDownInterval) { 
			super(millisInFuture, countDownInterval); 
		} 
		
		@Override 
		public void onFinish() { 
			textViewTime.setText(" 00:00"); 
			Toast timeUpToast = Toast.makeText(getApplicationContext(), 
			        "Time's Up!", Toast.LENGTH_LONG);
			    timeUpToast.show();
		} 
		
		@SuppressLint("NewApi") 
		@TargetApi(Build.VERSION_CODES.GINGERBREAD) 
		@Override public void onTick(long millisUntilFinished) { 
			long millis = millisUntilFinished; 
			String ms = String.format(Locale.US, " %01d:%02d",  
					TimeUnit.MILLISECONDS.toMinutes(millis), 
					TimeUnit.MILLISECONDS.toSeconds(millis) 
					- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))); 
			textViewTime.setText(ms); 
		} 
	} 
	
	

}
