package com.mosquefinder.arnal.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.arnal.mosquefinder.backend.jokeApi.JokeApi;
import com.example.arnal.mosquefinder.backend.jokeApi.model.Joke;
/*import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;*/
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.mosquefinder.arnal.androidlibrary.MainActivityAndroid;

import java.io.IOException;



 class EndpointsAsyncTask  extends AsyncTask<Pair<Context, String>, Void, String> {

     private ProgressBar progressBar;
     private static JokeApi myApi = null;
     private Context context;
     private String result;


     public EndpointsAsyncTask(Context context, ProgressBar progressBar) {
         this.context = context;
         this.progressBar = progressBar;
     }

     @Override
     protected void onPreExecute() {
         super.onPreExecute();
         if (progressBar != null) {
             progressBar.setVisibility(View.VISIBLE);
         }
     }

     @Override
     protected String doInBackground(Pair<Context, String>... params) {
         if (myApi == null) { //Only do once

             JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                     .setRootUrl("https://joke-teller-1.appspot.com/_ah/api/");
             //end options for dev app server

             myApi = builder.build();
         }


         try {

             return myApi.newJoke(new Joke()).execute().getData();
         } catch (IOException e) {
             return e.getMessage();
         }
     }

     @Override
     protected void onPostExecute(String result) {
         this.result = result;
         final InterstitialAd interstitialAd = new InterstitialAd(context);
         interstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_unit_id));
         interstitialAd.loadAd(new AdRequest.Builder().build());
         AdRequest adRequest = new AdRequest
                 .Builder()
                 .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                 .build();
         interstitialAd.loadAd(adRequest);
         interstitialAd.setAdListener(new AdListener() {
             @Override
             public void onAdLoaded() {
                 super.onAdLoaded();
                 if (progressBar != null) {
                     progressBar.setVisibility(View.GONE);
                 }
                 interstitialAd.show();
             }

             @Override
             public void onAdFailedToLoad(int i) {
                 super.onAdFailedToLoad(i);
                 if (progressBar != null) {
                     progressBar.setVisibility(View.GONE);
                 }
                 Toast.makeText(context, context.getString(R.string.interstitial_error), Toast.LENGTH_SHORT).show();
                 loadJokeDisplay();
             }

             @Override
             public void onAdClosed() {
                 super.onAdClosed();
                 loadJokeDisplay();
             }
         });
     }

     private void loadJokeDisplay() {
         Intent intent = new Intent(context, MainActivityAndroid.class);
         intent.putExtra(MainActivityAndroid.JOKE_KEY, result);
         context.startActivity(intent);
     }

 }

