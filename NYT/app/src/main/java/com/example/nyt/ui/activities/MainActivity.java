package com.example.nyt.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyt.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MOST_EMAILED_LINK = "/svc/mostpopular/v2/emailed/7.json?";
    private static final String MOST_VIEWED_LINK = "/svc/mostpopular/v2/viewed/1.json?";
    private static final String MOST_SHARED_LINK = "/svc/mostpopular/v2/shared/1/facebook.json?";
    private static final String MY_PREFS_NAME = "Shared_pref";

    private static final String LINK = "link";
    private static final String TITLE = "title";

    private static final String MOST_EMAILED_TITLE = "MOST EMAILED";
    private static final String MOST_SHARED_TITLE = "MOST SHARED";
    private static final String MOST_VIEWED_TITLE = "MOST VIEWED";


    TextView mostViewed;
    TextView mostShared;
    TextView mostEmailed;
    ImageView rightMostViewed;
    ImageView rightMostEmailed;
    ImageView rightMostShared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        tabSetup();

        mostViewed.setOnClickListener(this);
        mostShared.setOnClickListener(this);
        mostEmailed.setOnClickListener(this);
        rightMostViewed.setOnClickListener(this);
        rightMostShared.setOnClickListener(this);
        rightMostEmailed.setOnClickListener(this);

    }

    private void init() {
        mostViewed = findViewById(R.id.tv_most_viewed);
        mostEmailed = findViewById(R.id.tv_most_emailed);
        mostShared = findViewById(R.id.tv_most_shared);
        rightMostEmailed = findViewById(R.id.iv_right_emailed);
        rightMostShared = findViewById(R.id.iv_right_shared);
        rightMostViewed = findViewById(R.id.iv_right_viewed);
    }

    private void tabSetup() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.iv_right_viewed) || v == findViewById(R.id.tv_most_viewed)) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(LINK, MOST_VIEWED_LINK);
            editor.putString(TITLE, MOST_VIEWED_TITLE);
            editor.apply();
        } else if (v == findViewById(R.id.iv_right_shared) || v == findViewById(R.id.tv_most_shared)) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(LINK, MOST_SHARED_LINK);
            editor.putString(TITLE, MOST_SHARED_TITLE);
            editor.apply();

        } else if (v == findViewById(R.id.iv_right_emailed) || v == findViewById(R.id.tv_most_emailed)) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(LINK, MOST_EMAILED_LINK);
            editor.putString(TITLE, MOST_EMAILED_TITLE);
            editor.apply();
        }

        startActivity();
    }

    private void startActivity() {
        Intent myIntent = new Intent(MainActivity.this, ListingActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
