package com.easytag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class HttpController extends AsyncTask<URL, Integer, Long>  {
	
	public void start() throws ClientProtocolException, IOException, URISyntaxException {
	Log.e("test", "shoudl be running");
    HttpClient httpclient = new DefaultHttpClient();
	HttpGet httpget = new HttpGet();
	httpget.setURI(new URI("http://ajax.googleapis.com/"));
	// Add your data
	/*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));*/
	  
	//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	// Execute HTTP Post Request
    BufferedReader in = null;
	try {
	Log.e("test", "asdfadfas about to call execute");
	HttpResponse response = httpclient.execute(httpget);
	Log.e("test", "got past execute");
    in = new BufferedReader
    (new InputStreamReader(response.getEntity().getContent()));
    StringBuffer sb = new StringBuffer("");
    String line = "";
    String NL = System.getProperty("line.separator");
    while ((line = in.readLine()) != null) {
        sb.append(line + NL);
    }
    in.close();
    Log.e("test", "closed input buffer");
    String page = sb.toString();
    Log.e("test", page);
    Log.e("success", page);
    } finally {
	    if (in != null) {
	        try {
	            in.close();
	            } catch (IOException e) {
	            e.printStackTrace();
	        }
		Log.e("test", "finished doing execute");
		}
    }
	}

	@Override
	protected Long doInBackground(URL... arg0) {
		try {
			this.start();
			Log.e("test", "success!!!!!!");
			}
			catch ( ClientProtocolException exc ) {
				Log.e("test", "client protocol exception");
			}
			catch ( IOException exc ) {
				Log.e("test", "IO exception");
				Log.e("test", exc.toString());
			}
			catch ( URISyntaxException exc ) { 
				Log.e("test", " uri sytntax exception");
			}
			catch ( Exception e ) {
				Log.e("test", e.toString());
				Log.e("test","FUCKCCKCKCKCKCKCK");
			}
		return null;
	}
}
