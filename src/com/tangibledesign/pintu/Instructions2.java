package com.tangibledesign.pintu;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Instructions2 extends FragmentActivity implements ActionBar.TabListener {

	    /**
	     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
	     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
	     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	     */
	    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	    /**
	     * The {@link ViewPager} that will display the three primary sections of the app, one at a
	     * time.
	     */
	    ViewPager mViewPager;

	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_instructions2);

	        // Create the adapter that will return a fragment for each of the three primary sections
	        // of the app.
	        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

	        // Set up the action bar.
	        final ActionBar actionBar = getActionBar();

<<<<<<< HEAD
//	        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
//	        // parent.
//	        actionBar.setHomeButtonEnabled(t);
=======
	        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
	        // parent
	        actionBar.setHomeButtonEnabled(false);
>>>>>>> FETCH_HEAD

	        // Specify that we will be displaying tabs in the action bar.
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
	        // user swipes between sections.
	        mViewPager = (ViewPager) findViewById(R.id.pager);
	        mViewPager.setAdapter(mAppSectionsPagerAdapter);
	        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
	            @Override
	            public void onPageSelected(int position) {
	                // When swiping between different app sections, select the corresponding tab.
	                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
	                // Tab.
	                actionBar.setSelectedNavigationItem(position);
	            }
	        });

	        // For each of the sections in the app, add a tab to the action bar.
	        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
	            // Create a tab with text corresponding to the page title defined by the adapter.
	            // Also specify this Activity object, which implements the TabListener interface, as the
	            // listener for when this tab is selected.
	            actionBar.addTab(
	                    actionBar.newTab()
	                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
	                            .setTabListener(this));
	        }
	    }

	    @Override
	    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	    }

	    @Override
	    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	        // When the given tab is selected, switch to the corresponding page in the ViewPager.
	        mViewPager.setCurrentItem(tab.getPosition());
	    }

	    @Override
	    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	    }

	    /**
	     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
	     * sections of the app.
	     */
	    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

	        public AppSectionsPagerAdapter(FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public Fragment getItem(int i) {
	            switch (i) {
	                case 0:
	                    // The first section of the app is the most interesting -- it offers
	                    // a launchpad into the other demonstrations in this example application.
	                    return new LaunchpadSectionFragment();

	                default:
	                    // The other sections of the app are dummy placeholders.
	                    Fragment fragment = new DummySectionFragment();
	                    Bundle args = new Bundle();
	                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
	                    fragment.setArguments(args);
	                    return fragment;
	            }
	        }

	        @Override
	        public int getCount() {
	            return 3;
	        }

	        @Override
	        public CharSequence getPageTitle(int position) {
	            String title = null;
				
	            if (position == 0) {
	            	title = "Rules";
	            }
	        	
	            if (position == 1) {
	            	title = "Buttons";
	            }
	            
	            if (position == 2) {
	            	title = "About";
	            }
	            
	            return title;
	        }
	    }

	    /**
	     * A fragment that launches other parts of the demo application.
	     */
	    public static class LaunchpadSectionFragment extends Fragment {

	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
	            return rootView;
	        }
	    }
	    

	    /**
	     * A dummy fragment representing a section of the app, but that simply displays dummy text.
	     */
	    public static class DummySectionFragment extends Fragment {

	        public static final String ARG_SECTION_NUMBER = "section_number";

	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
	            Bundle args = getArguments();
	            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
	                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
	            return rootView;
	        }
	    }
	}