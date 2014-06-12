package com.example.ghfablab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends Activity {
	static String IP_ADDRESS = "NOT SET";
	
	Button button1;
	EditText editText1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		button1 = (Button)findViewById(R.id.button1);
	    editText1 = (EditText)findViewById(R.id.editText1);
		
		button1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				IP_ADDRESS = editText1.getText().toString();
				showToast("IP is set to "+IP_ADDRESS);
			}
		});
	}
	
	 protected void showToast(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();		  	
	}

	
	
	
}
