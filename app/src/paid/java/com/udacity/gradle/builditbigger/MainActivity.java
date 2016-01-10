package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity implements EndpointTask.EndpointTaskListener {
    private static final String EXTRA_BUNDLE_TAG = "bool_joke_tag";
    private ProgressBar mProgressBar;
    private Button jokeButton;
    private Boolean retrievingJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        jokeButton = (Button) findViewById(R.id.joke_button);

        if(savedInstanceState!=null){
            retrievingJoke = savedInstanceState.getBoolean(EXTRA_BUNDLE_TAG, false);
        }else {
            retrievingJoke = false;
        }

        if(retrievingJoke) {
            jokeButton.setEnabled(false);
            mProgressBar.setVisibility(View.VISIBLE);
        }else{
            jokeButton.setEnabled(true);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_BUNDLE_TAG, retrievingJoke);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        retrievingJoke = true;
        jokeButton.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        EndpointTask retrieveJoke = new EndpointTask(false, this);
        retrieveJoke.execute(MainActivity.this);
    }

    @Override
    public void onComplete(String result, String error) {
        retrievingJoke = false;
    }
}
