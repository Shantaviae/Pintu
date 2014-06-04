package com.tangibledesign.pintu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class Tab1Activity extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            TextView  tv=new TextView(this);
            tv.setTextSize(25);
            tv.setText("This Is Tab1 Activity");
            
            setContentView(tv);
        }
}