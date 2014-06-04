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
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Instructions extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    // Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();
        
        TabSpec tab1 = tabHost.newTabSpec("Rules");
        TabSpec tab2 = tabHost.newTabSpec("Buttons");
        TabSpec tab3 = tabHost.newTabSpec("About");
        
        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
         tab1.setIndicator("Rules");
         tab1.setContent(new Intent(this,Tab1Activity.class));
         
         tab2.setIndicator("Buttons");
         tab2.setContent(new Intent(this,Tab2Activity.class));

         tab3.setIndicator("About");
         tab3.setContent(new Intent(this,Tab3Activity.class));
         
         /** Add the tabs  to the TabHost to display. */
         tabHost.addTab(tab1);
         tabHost.addTab(tab2);
         tabHost.addTab(tab3);
         
	    // Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.instructions, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_instructions,
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

}
