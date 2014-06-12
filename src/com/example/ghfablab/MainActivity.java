package com.example.ghfablab;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	private String[] data = {"Configure server", "Register content"};
	private String[] activities = {"ConfigActivity","RegisterActivity"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, data);
		setListAdapter(adapter);
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
		 //Toast.makeText(this, activities[position], Toast.LENGTH_SHORT).show();	
			//FOR ANDROID 4(shohei)
		    Intent intent = new Intent();
			intent.setClassName("com.example.ghfablab","com.example.ghfablab."+activities[position]);
			//FOR OTHERS
			 //Intent intent = new Intent(this, RegisterActivity.class); 
			startActivity(intent);	
	}
		
	

}
