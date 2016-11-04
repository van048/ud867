package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import cn.ben.JokeDisplayActivity;


public class MainActivity extends AppCompatActivity {

    private static final String MY_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private InterstitialAd mInterstitialAd;
    private GetJokeEndpointsAsyncTask mGetJokeEndpointsAsyncTask;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the mInterstitialAd.
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId(MY_AD_UNIT_ID);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.INVISIBLE);
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

    public void tellJoke(@SuppressWarnings("UnusedParameters") View view) {
        // should check whether a task is running
        if (mGetJokeEndpointsAsyncTask != null) {
            Toast.makeText(this, "Task is running!", Toast.LENGTH_SHORT).show();
            return;
        }
        mGetJokeEndpointsAsyncTask = new GetJokeEndpointsAsyncTask(new GetJokeEndpointsAsyncTask.OnTaskFinishedListener() {
            @Override
            public void handle(final String result) {
                // Create ad request.
                AdRequest adRequest = new AdRequest.Builder().build();
                // Begin loading your mInterstitialAd.
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        Toast.makeText(MainActivity.this, "Check your network!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        mInterstitialAd.show();
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();

                        Intent intent = new Intent(MainActivity.this, JokeDisplayActivity.class);
                        intent.putExtra(JokeDisplayActivity.EXTRA_JOKE, result);
                        startActivity(intent);

                        mGetJokeEndpointsAsyncTask = null;
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
        mGetJokeEndpointsAsyncTask.execute(this);
    }


}
