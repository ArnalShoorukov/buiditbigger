package com.mosquefinder.arnal.androidlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivityAndroid extends AppCompatActivity {

    public static String JOKE_KEY = "Joke key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_android);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(JOKE_KEY);


        TextView textView = (TextView)findViewById(R.id.textView);
        if(joke != null && joke.length() != 0){
            textView.setText(joke);
        }


        TextView jokeTextView = (TextView)findViewById(R.id.joke_textview);

        if(joke != null && joke.length() != 0){
            jokeTextView.setText(joke);
        }
    }
}
