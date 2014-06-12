package com.example.ghfablab;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View.OnClickListener;

public class AsyncHttpRequest extends AsyncTask<Uri.Builder, Void, String> {
    
    private Activity mainActivity;

    public AsyncHttpRequest(OnClickListener onClickListener) {
        // 呼び出し元のアクティビティ
        //this.mainActivity = onClickListener;
    }
    
    // このメソッドは必ずオーバーライドする必要があるよ
    // ここが非同期で処理される部分みたいたぶん。
	protected String doInBackground(Builder... arg0) {
		// TODO Auto-generated method stub
		post("http://192.168.1.100:3000/products","hogename","hogeq","hogeloc","hogenote",RegisterActivity.image_path);
		return null;
	}

	public void post(String url,String name, String quantity, String location, String note, String image_filepath) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        //HttpPost httpPost = new HttpPost(url);
        HttpPost httpPost = new HttpPost("http://192.168.1.100:3000/products");
        httpPost.addHeader("content_type","image/jpeg");
        try {
        	MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,null,Charset.forName("UTF-8"));
        	//MultipartEntity multipartEntity = new MultipartEntity();
        	multipartEntity.addPart("product[name]", new StringBody(name,Charset.forName("UTF-8")));
        	multipartEntity.addPart("product[quantity]", new StringBody(quantity,Charset.forName("UTF-8")));
        	multipartEntity.addPart("product[location]", new StringBody(location,Charset.forName("UTF-8")));
        	multipartEntity.addPart("product[note]", new StringBody(note,Charset.forName("UTF-8")));
        	
        	File imgFile = new File(image_filepath);
        	//FileBody fileBody = new FileBody(imgFile.getAbsoluteFile(), "image/jpeg");
        	//FileBody fileBody = new FileBody(imgFile, "image/jpeg","UTF-8");
        	FileBody fileBody = new FileBody(imgFile, "application/octet-stream","UTF-8");
        	
        	FileInputStream in = new FileInputStream(imgFile);
        	InputStreamBody streamBody = new InputStreamBody(in, "image/jpeg","shutter_plugin_sample.jpg");
        	//multipartEntity.addPart("product[photo]", fileBody);
        	multipartEntity.addPart("product[photo]", streamBody);
        	
        	//UrlEncodedFormEntity ent = new UrlEncodedFormEntity((List<? extends NameValuePair>) multipartEntity,HTTP.UTF_8);
        	httpPost.setEntity(multipartEntity);
        	//httpPost.setEntity(ent);
        	HttpResponse response = httpClient.execute(httpPost, localContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // このメソッドは非同期処理の終わった後に呼び出されます
    @Override
    protected void onPostExecute(String result) {
    	//TextView tv = (TextView) mainActivity.findViewById(R.id.name);
        //tv.setText(result)
    }

	@Override
	protected String doInBackground(android.net.Uri.Builder... arg0) {
		// TODO Auto-generated method stub
		post("192.168.1.100","hogename","hogeq","hogeloc","hogenote",RegisterActivity.image_path);
		return null;
	}

}
