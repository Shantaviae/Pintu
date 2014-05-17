package com.tangibledesign.pintu;

import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.provider.MediaStore;
import android.graphics.Typeface;

public class LearnActivity extends ActionBarActivity {

	//custom drawing view
	public DrawingView drawView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn);
		
		Typeface font = Typeface.createFromAsset( getAssets(), "FontAwesome.otf" );
		
		Button btn_clear = (Button)findViewById( R.id.new_btn );
		Button btn_erase = (Button)findViewById( R.id.erase_btn );
		Button btn_draw = (Button)findViewById( R.id.draw_btn );
		Button btn_next = (Button)findViewById( R.id.next_btn );
		
		btn_clear.setTypeface(font);
		btn_erase.setTypeface(font);
		btn_next.setTypeface(font);
		btn_draw.setTypeface(font);
		
		drawView = (DrawingView) findViewById(R.id.drawing);
	}
	
	public void startDraw (View view){
		drawView.setErase(false);
		
	}
	
	public void startEraser (View view){
		drawView.setErase(true);
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
				    savedToast.show();
				    drawView.startNew();
				    drawView.destroyDrawingCache();
				    drawView.setErase(false);
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
		getMenuInflater().inflate(R.menu.learn, menu);
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
}
