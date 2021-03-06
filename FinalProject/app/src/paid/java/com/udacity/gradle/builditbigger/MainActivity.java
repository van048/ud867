package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.ben.JokeDisplayActivity;


public class MainActivity extends AppCompatActivity {
    private GetJokeEndpointsAsyncTask mGetJokeEndpointsAsyncTask;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void tellJoke(View view) {
        // should check whether a task is running
        if (mGetJokeEndpointsAsyncTask != null) {
            Toast.makeText(this, "Task is running!", Toast.LENGTH_SHORT).show();
            return;
        }
        mGetJokeEndpointsAsyncTask = new GetJokeEndpointsAsyncTask(new GetJokeEndpointsAsyncTask.OnTaskFinishedListener() {
            @Override
            public void handle(String result) {
                Intent intent = new Intent(MainActivity.this, JokeDisplayActivity.class);
                intent.putExtra(JokeDisplayActivity.EXTRA_JOKE, result);
                startActivity(intent);
                mGetJokeEndpointsAsyncTask = null;
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
        mGetJokeEndpointsAsyncTask.execute(this);
    }


}
