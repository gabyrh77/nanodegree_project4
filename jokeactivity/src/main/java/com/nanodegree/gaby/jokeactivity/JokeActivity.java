package com.nanodegree.gaby.jokeactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    public static final String JOKE_INTENT_EXTRA = "joke_intent";
    private String joke;
    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.getString(JOKE_INTENT_EXTRA)!=null ){
            joke = extras.getString(JOKE_INTENT_EXTRA);
        }
        jokeTextView = (TextView) findViewById(R.id.textView);
        if(jokeTextView!=null){
            if (joke!=null && !joke.isEmpty()){
                jokeTextView.setText(joke);
            }else{
                jokeTextView.setText(getString(R.string.no_joke));
            }
        }
    }


}
