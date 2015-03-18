/**
 * The Asynchronous JAVA HTTP Connection For Android.
 * : HttpConnection Class & HttpConnectionHandler Interface
 *
 * @version 1.0
 * @author Misam Saki, http://misam.ir/
 * @document https://github.com/misamplus/HttpConnection
 *
 * Copyright © 2000 Misam Saki <hi@misam.ir>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class HttpConnection extends AsyncTask<Void, Void, Void> {

    private HttpConnectionHandler httpConnectionHandler;
    private ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    private String url;
    private String response;
    private String responseCharset = "utf-8";
    private boolean fault = true;
    private String error;

    public HttpConnection(String url, HttpConnectionHandler httpConnectionHandler) {
        this.url = url;
        this.httpConnectionHandler = httpConnectionHandler;
    }

    public HttpConnection(String url, String outputCharset, HttpConnectionHandler httpConnectionHandler) {
        this.url = url;
        this.responseCharset = outputCharset;
        this.httpConnectionHandler = httpConnectionHandler;
    }

    public void setHttpConnectionHandler(HttpConnectionHandler httpConnectionHandler) {
        this.httpConnectionHandler = httpConnectionHandler;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPostParameters(ArrayList<NameValuePair> postParameters) {
        this.postParameters = postParameters;
    }

    public void clearPostParameters() {
        postParameters = new ArrayList<NameValuePair>();
    }

    public void addPostParameters(String key, String value) {
        if (postParameters == null) clearPostParameters();
        postParameters.add(new BasicNameValuePair(key, value));
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        httpConnectionHandler.onStart();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse;
            if (postParameters == null) clearPostParameters();
            if (postParameters.isEmpty()) {
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
            } else {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
                httpResponse = httpClient.execute(httpPost);
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity, responseCharset);
            fault = false;
        } catch (UnsupportedEncodingException e) {
            error = e.getMessage();
        } catch (MalformedURLException e) {
            error = e.getMessage();
        } catch (IOException e) {
            error = e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (fault) httpConnectionHandler.onFault(response, error); else httpConnectionHandler.onFinish(response);
    }
}

interface HttpConnectionHandler {
    void onStart();
    void onFinish(String response);
    void onFault(String response, String error);
}
