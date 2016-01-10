package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nanodegree.gaby.jokeactivity.JokeActivity;
import com.nanodegree.gaby.jokebackend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by gaby_ on 3/1/2016.
 */
public class EndpointTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private EndpointTaskListener mListener;
    private String mError;
    private boolean fromTest;

    public EndpointTask(boolean fromTest, EndpointTaskListener listener){
        this.fromTest = fromTest;
        this.mListener = listener;
    }

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://nanodegree-4.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            this.mError = e.getMessage();
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(this.mListener!=null){
            this.mListener.onComplete(result, mError);
        }
        if(!fromTest){
            Intent jokeIntent = new Intent(context, JokeActivity.class);
            jokeIntent.putExtra(JokeActivity.JOKE_INTENT_EXTRA, result);
            context.startActivity(jokeIntent);
        }
    }

    @Override
    protected void onCancelled() {
        if(this.mListener!=null){
            this.mListener.onComplete(null, "Error: onCancelled was called.");
        }
    }

    public interface EndpointTaskListener{
        void onComplete(String result, String error);
    }


}
