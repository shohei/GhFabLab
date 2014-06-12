package com.example.ghfablab;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
				  showToast("Please set server IP!");
				} else {
					showToast("Server IP is"+SERVER_IP);
					//post(CURRENT_IP+":3000/products","hogename","hogequantity","hogelocation","hogenote","filename.jpeg");					
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
		}
	  }
	
	public void post(String url,String name, String quantity, String location, String note, String image_filename) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("content_type","image/jpeg");
        try {
        	MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        	File imgFile = new File(image_filename);
        	FileBody fileBody = new FileBody(imgFile, "image/jpeg");
        	multipartEntity.addPart("product[photo]", fileBody);
             
        	httpPost.setEntity(multipartEntity);
        	HttpResponse response = httpClient.execute(httpPost, localContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 protected void showToast(String message){
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();		  	
		}
    
	
		
}
