package com.tangibledesign.pintu;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
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

import com.tangibledesign.pintu.R;


@SuppressLint("NewApi")
public class EasyActivity extends ActionBarActivity {

	//custom drawing view
	public DrawingView drawView;
	TextView textViewTime;
	TextView textViewScore;
	public int score = 0;
	CounterClass timer;
	public ArrayList<String> gameImgs = new ArrayList<String>(); 
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy);
		
		
		Typeface font = Typeface.createFromAsset( getAssets(), "FontAwesome.otf" );
		
		Button btn_clear = (Button)findViewById( R.id.new_btn );
		Button btn_save = (Button)findViewById( R.id.submit_btn );
		Button btn_draw = (Button)findViewById( R.id.draw_btn );
		Button btn_erase = (Button)findViewById( R.id.erase_btn );
		
		btn_clear.setTypeface(font);
		btn_save.setTypeface(font);
		btn_draw.setTypeface(font);
		btn_erase.setTypeface(font);
		
		drawView = (DrawingView) findViewById(R.id.drawing);
		
		textViewTime = (TextView)findViewById(R.id.timer);  
		textViewScore = (TextView)findViewById(R.id.score); 
		
		textViewTime.setText(" 02:01"); 
        textViewScore.setText(" 0");
        timer = new CounterClass(121000,1000); 
        timer.start();
	}
	
	public void startDraw (View view){
		drawView.setErase(false);
	}
	
	public void startEraser (View view){
		drawView.setErase(true);
	}

	public void endGame() {
		
		drawView.setErase(true);
		drawView.setDraw(false);
		
		score = 0;
		
		MediaPlayer mpCongrats = MediaPlayer.create(getApplicationContext(), R.raw.congrats);
		mpCongrats.start();
		
		AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
		newDialog.setTitle("Time Up! 时间长达! (Shíjiān zhǎng dá)");
		newDialog.setMessage("Congratulations! 恭喜! (Gōngxǐ) \nYour score is " + score + ".\n\n Would you like to play again?");
		newDialog.setPositiveButton("Replay", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		    	restartGame();
		    }
		});
		newDialog.setNegativeButton("Return to Menu", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		        returnToMenu();
		    }
		});
		newDialog.show();
		
	}
	
	/** Called when the user clicks the Return to Menu button on the play page */
    public void returnToMenu() {
        //Start game by taking the user to choose which level they want to begin at
    	Intent intent = new Intent(this, LevelActivity.class);
    	startActivity(intent);
    }
	
	public void restartGame(){
		gameImgs.clear();
		drawView.setErase(false);
		drawView.setDraw(true);
		
		textViewTime.setText(" 02:01"); 
        textViewScore.setText(" 0");
        timer = new CounterClass(121000,1000); 
        timer.start();
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		timer.cancel();
	}
	
	public void clearScreen(View view){
		AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
		newDialog.setTitle("Clear Attempt");
		newDialog.setMessage("Start new character?\nNOTE: You will lose the current attempt.");
		newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		        drawView.startNew();
		        drawView.setErase(false);
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
				gameImgs.add(imgSaved);
				
				if(imgSaved!=null){
				    Toast savedToast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
				    
				    addScore();
				    savedToast.show();
				    drawView.startNew();
				    drawView.destroyDrawingCache();
				    drawView.setErase(false);
				}
				else{
					MediaPlayer mpCongrats = MediaPlayer.create(getApplicationContext(), R.raw.wronganswer);
					mpCongrats.start();
				    Toast unsavedToast = Toast.makeText(getApplicationContext(), 
				        "Incorrect! 答错了! (Dá cuòle)", Toast.LENGTH_SHORT);
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
		getMenuInflater().inflate(R.menu.easy, menu);
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
			MediaPlayer mpCongrats = MediaPlayer.create(getApplicationContext(), R.raw.timesup);
			mpCongrats.start();
			textViewTime.setText(" 00:00"); 
			Toast timeUpToast = Toast.makeText(getApplicationContext(), 
			        "Time's Up!", Toast.LENGTH_LONG);
			timeUpToast.show();
			endGame();
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
