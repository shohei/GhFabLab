package com.example.ghfablab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	static final int REQUEST_CAPTURE_IMAGE = 100;
	Button button1,button2;
	ImageView imageView1;
	String SERVER_IP;
	static String image_path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		findViews();
		setListeners();
		button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				SERVER_IP = ConfigActivity.IP_ADDRESS;
				if (SERVER_IP == "NOT SET"){
					showAlert("Server configuration error","Please set IP address.");
				} else {
					//showAlert("Upload data","Server IP is"+SERVER_IP+". Are you sure to send?");					
					showToast("Data is uploaded to server "+SERVER_IP);
					AsyncHttpRequest task = new AsyncHttpRequest(this);
					task.execute();
				}
			  }
			});
	}

	protected void findViews(){
		button1 = (Button)findViewById(R.id.button1);
		imageView1 = (ImageView)findViewById(R.id.imageView1);
	}
	
	protected void setListeners(){
		button1.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(
					intent,
					REQUEST_CAPTURE_IMAGE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(REQUEST_CAPTURE_IMAGE == requestCode 
			&& resultCode == Activity.RESULT_OK ){
			Bitmap capturedImage = 
				(Bitmap) data.getExtras().get("data");
			imageView1.setImageBitmap(capturedImage);
			Uri selectedImageUri = data.getData();
			image_path = getRealPathFromURI(selectedImageUri);
			//showToast(image_path);
		}
	  }
	

	
	 protected void showToast(String message){
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();		  	
		}
    
	 protected void showAlert(String title,String message){
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	        alertDialogBuilder.setTitle(title);
	        alertDialogBuilder.setMessage(message);
	        alertDialogBuilder.setPositiveButton("Confirm",
	                new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    }
	                });
	        alertDialogBuilder.setCancelable(true);
	        AlertDialog alertDialog = alertDialogBuilder.create();
	        alertDialog.show();
	 }
	 public String getRealPathFromURI(Uri contentUri)
	    {
	        try
	        {
	            String[] proj = {MediaStore.Images.Media.DATA};
	            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            cursor.moveToFirst();
	            return cursor.getString(column_index);
	        }
	        catch (Exception e)
	        {
	            return contentUri.getPath();
	        }
	    }
		
}
