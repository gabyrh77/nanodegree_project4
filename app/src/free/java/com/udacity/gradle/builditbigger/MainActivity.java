package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity implements EndpointTask.EndpointTaskListener {
    private static final String EXTRA_BUNDLE_TAG = "bool_joke_tag";
    private InterstitialAd mInterstitialAd;
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

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitialAd();
                executeTellJoke();
            }
        });

        requestNewInterstitialAd();
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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            executeTellJoke();
        }
    }

    private void executeTellJoke(){
        retrievingJoke = true;
        jokeButton.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        EndpointTask retrieveJoke = new EndpointTask(false, this);
        retrieveJoke.execute(MainActivity.this);
    }

    private void requestNewInterstitialAd(){
        if(mInterstitialAd!=null) {
            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }
    }


    @Override
    public void onComplete(String result, String error) {
        retrievingJoke = false;
    }
}
