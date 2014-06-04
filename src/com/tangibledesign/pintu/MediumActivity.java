package com.tangibledesign.pintu;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
public class MediumActivity extends ActionBarActivity {

	//custom drawing view
	public DrawingView drawView;
	TextView textViewTime;
	TextView textViewScore;
	public int score = 0;
	CounterClass timer;
	Typeface font;
	public ArrayList<String> gameImgs = new ArrayList<String>(); 
	ImageView imgResults;
	int index = 0;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medium);
		
		
		font = Typeface.createFromAsset( getAssets(), "FontAwesome.otf" );
		
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
		
		imgResults = (ImageView)findViewById(R.id.results);
		
		startGame();
	}
	
	public void startGame () {
		
		AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
		newDialog.setTitle("Game Instructions");
		newDialog.setMessage("Use one or more of the radical(s) provided to form as many characters as possible!"
				+ "\n\nRemember that you only have two minutes before the clock runs out!\n");
		newDialog.setNeutralButton("Start Game!", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		    	textViewTime.setText(" 00:31"); 
		        textViewScore.setText(" 0");
		        timer = new CounterClass(31000,1000); 
		        timer.start();
		    }
		});
		newDialog.show();
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
		
		MediaPlayer mpCongrats = MediaPlayer.create(getApplicationContext(), R.raw.timesup);
		mpCongrats.start();
		
		AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
		newDialog.setTitle("Time is up! 时间长达! (Shíjiān zhǎng dá)");
		newDialog.setMessage("Congratulations! 恭喜! (Gōngxǐ) \nYour score is " + score + ".\n\n Would you like to play again?");
		newDialog.setPositiveButton("Replay", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		    	clearSavedImgs();
		    	restartGame();
		    }
		});
		/*newDialog.setNeutralButton("Review submissions", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		    	displaySubmissions();
		    }
		});*/
		newDialog.setNegativeButton("Return to Menu", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which){
		    	clearSavedImgs();
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
		
		//Delete arrayList of paths to images saved
		gameImgs.clear();
		drawView.setErase(false);
		drawView.setDraw(true);
		
		//Clear results imageview
		imgResults.setImageResource(R.drawable.clear);
		
    	textViewTime.setText(" 00:31"); 
        textViewScore.setText(" 0");
        timer = new CounterClass(31000,1000); 
        timer.start();
		
	}
	
	public void displaySubmissions(){
		
		// Create custom dialog object
        final Dialog dialog = new Dialog(MediumActivity.this);
        
        // Include dialog.xml file
        dialog.setContentView(R.layout.gallery_dialog);
        // Set dialog title
        dialog.setTitle("Submmisson Gallery");
        
        Button next_btn = (Button)findViewById( R.id.nextButton);
        Button main_btn = (Button) dialog.findViewById(R.id.mainMenuButton);
        Button replay_btn = (Button) dialog.findViewById(R.id.replayButton);
        
        next_btn.setTypeface(font);

        // set values for custom dialog components - text, image and button
        ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
        image.setImageURI(Uri.parse(gameImgs.get(index)));

        dialog.show();
         
        
        // if decline button is clicked, close the custom dialog
        next_btn.setOnClickListener(myhandler);
        main_btn.setOnClickListener(myhandler);
        replay_btn.setOnClickListener(myhandler);
        
	}
	
	View.OnClickListener myhandler = new View.OnClickListener() {
		  public void onClick(View v) {
		      switch(v.getId()) {
		        case R.id.nextButton:
		          // it was the next button
		          break;
		        case R.id.mainMenuButton:
		          // it was the main menu button
		          break;
		        case R.id.replayButton:
			      // it was the replay button
			      break;
		      }
		  }
		};
	public void clearSavedImgs() {
		
		for (int i = 0; i < gameImgs.size(); i++){
			File image = new File(gameImgs.get(i)); 
			image.delete();
		}
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
				imgResults.setImageURI(Uri.parse(imgSaved));
				
				if(imgSaved!=null){
					
					MediaPlayer mpCongrats = MediaPlayer.create(getApplicationContext(), R.raw.congrats);
					mpCongrats.start();
					
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
			//Says time up in Chinese but conflicts with the congratulations! must be fixed in later version
			//MediaPlayer mpCongrats = MediaPlayer.create(getApplicationContext(), R.raw.timesup);
			//mpCongrats.start();
			//Conflicts with the end of the game dialog
			//Toast timeUpToast = Toast.makeText(getApplicationContext(), 
			//        "Time's Up!", Toast.LENGTH_LONG);
			//timeUpToast.show();
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
