package cn.ben;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        TextView textViewDisplayJoke = (TextView) findViewById(R.id.tv_display_joke);
        textViewDisplayJoke.setText(getIntent().getStringExtra(EXTRA_JOKE));
    }
}
