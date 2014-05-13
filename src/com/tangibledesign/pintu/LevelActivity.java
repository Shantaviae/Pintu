package com.tangibledesign.pintu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class LevelActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_level,
					container, false);
			return rootView;
		}
	}
	
	/** Force activity to always use landscape mode */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
      // ignore orientation/keyboard change
      super.onConfigurationChanged(newConfig);
    }
    
    /** Called when the user clicks the play button on the home page */
    public void startLearnGame(View view) {
        //Start game by taking the user to choose which level they want to begin at
    	Intent intent = new Intent(this, LearnActivity.class);
    	startActivity(intent);
    }
    
    /** Called when the user clicks the play button on the home page */
    public void startEasyGame(View view) {
        //Start game by taking the user to choose which level they want to begin at
    	Intent intent = new Intent(this, EasyActivity.class);
    	startActivity(intent);
    }
    
    /** Called when the user clicks the play button on the home page */
    public void startMediumGame(View view) {
        //Start game by taking the user to choose which level they want to begin at
    	Intent intent = new Intent(this, MediumActivity.class);
    	startActivity(intent);
    }
    
    /** Called when the user clicks the play button on the home page */
    public void startHardGame(View view) {
        //Start game by taking the user to choose which level they want to begin at
    	Intent intent = new Intent(this, HardActivity.class);
    	startActivity(intent);
    }

}
