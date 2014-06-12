package com.example.ghfablab;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UploadAsyncTask 
  extends AsyncTask<String, Integer, Integer> {

  ProgressDialog dialog;
  Context context;
  
  public UploadAsyncTask(Context context){
    this.context = context;
  }
  
  @Override
  protected Integer doInBackground(String... params) {

    try {
      String fileName = params[0];
      
      HttpClient httpClient = new DefaultHttpClient();
      HttpPost httpPost = new HttpPost("192.168.1.100:3000/products");
      ResponseHandler<String> responseHandler =
        new BasicResponseHandler();
      MultipartEntity multipartEntity =
        new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
      
      File file = new File(fileName);
      FileBody fileBody = new FileBody(file, "image/jpeg");
      multipartEntity.addPart("f1", fileBody);
      
      httpPost.setEntity(multipartEntity);
      httpClient.execute(httpPost, responseHandler);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return 0;
  }

  @Override
  protected void onPostExecute(Integer result) {
    if(dialog != null){
      dialog.dismiss();
    }
  }

  @Override
  protected void onPreExecute() {
    dialog = new ProgressDialog(context);
    dialog.setTitle("Please wait");
    dialog.setMessage("Uploading...");
    dialog.show();
  }  
}
