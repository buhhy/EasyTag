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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.easytag.helper.AsyncCallback;
import com.easytag.model.Image;
import com.easytag.model.Tag;

public class HttpController extends AsyncTask<URL, Integer, Object> {

	public enum RequestMethod {
		 GET, POST
	}
	private HttpClient httpclient;
	private RequestMethod requestMethod;
	private List<NameValuePair> postArgs;
	private MainActivity listener;
	
	public AsyncCallback caller;
	
	public HttpController(MainActivity listener) {
		this.httpclient = new DefaultHttpClient();
		this.requestMethod = RequestMethod.GET;
		this.postArgs = new ArrayList<NameValuePair>();
		this.listener = listener;
	}

	public void setPostArgs(List<NameValuePair> postArgs) {	
		this.postArgs.clear();
		this.postArgs = postArgs;
	}
	public List<NameValuePair> getPostArgs() {	
		return this.postArgs;
	}
	public void setRequestMethod(RequestMethod method) {	
		this.requestMethod = method;
	}
	public RequestMethod getRequestMethod(RequestMethod method) {	
		return this.requestMethod;
	}

	public String readResponse(HttpResponse response) throws IllegalStateException, IOException {
		String result = "";
		BufferedReader in = null;
		try {
		    in = new BufferedReader
		    (new InputStreamReader(response.getEntity().getContent()));
		    StringBuffer sb = new StringBuffer("");
		    String line = "";
		    String NL = System.getProperty("line.separator");
		    while ((line = in.readLine()) != null) {
		        sb.append(line + NL);
		    }
		    in.close();
		    result = sb.toString();
		    } finally {
			    if (in != null) {
			        try {
			            in.close();
			            } catch (IOException e) {
			            e.printStackTrace();
			        }
				}
		    }
		    return result;
	}

	public String httpPost(String url, List<NameValuePair> args) throws ClientProtocolException, IOException, URISyntaxException {
		HttpGet httppost = new HttpGet(url);
		HttpResponse response = null;
		((HttpResponse) httppost).setEntity(new UrlEncodedFormEntity(args));
		response = httpclient.execute(httppost);
		Log.v("test", "POST request successfully executed");
		String result = readResponse(response);
		Log.v("test", result);
		return result;
	}

	public String httpGet(String url) throws ClientProtocolException, IOException, URISyntaxException {
		HttpGet httpget = new HttpGet();
		httpget.setURI(new URI(url));
		HttpResponse response = null;
		response = httpclient.execute(httpget);
		Log.v("test", "GET request successfully executed");
		String result = readResponse(response);
		Log.v("test", result);
		return result;
	}

	@Override
	protected Object doInBackground(URL... arg0) {
		String result = "";
		try { 
			if (this.requestMethod == RequestMethod.GET) {
				for (URL u : arg0) {
					result += this.httpGet(u.toString());
				}
			}
			else if (this.requestMethod == RequestMethod.POST) {
				for (URL u : arg0) {
					result += this.httpPost(u.toString(), this.postArgs);
				}
			}
			return caller.call(result);
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
	
	protected void onPostExecute(Object result){
		if(result instanceof Tag)
			listener.onUpdateTags();
		else if(result instanceof Image)
			listener.onUpdateImage();
	}

}
