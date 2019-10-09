package com.example.nyt.ui.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyt.R;
import com.example.nyt.utils.BaseUtlis;
import com.example.nyt.utils.DetailsParceableObject;


public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPrefs;
    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "listingObject";
    TextView sub_text;
    TextView published_date;
    TextView byLine;
    TextView abstract_value;
    TextView title;

    ImageView love;
    DetailsParceableObject detailsParceableObject;
    ImageView image;
    BaseUtlis baseUtlis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        findViewById(R.id.iv_close).setOnClickListener(this);


    }

    public void init() {
        sharedPrefs = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        title = findViewById(R.id.tv_details_title);
        sub_text = findViewById(R.id.tv_sub_text);
        published_date = findViewById(R.id.tv_published_date);
        byLine = findViewById(R.id.tv_source_value);
        abstract_value = findViewById(R.id.tv_abstract_value);
        love = findViewById(R.id.iv_love_details);
        detailsParceableObject = getIntent().getParcelableExtra(DETAILS_PARCEABLE_OBJECT);
        image = findViewById(R.id.iv_details_banner);
        baseUtlis = new BaseUtlis();
        populateUi();


    }

    public void populateUi() {
        title.setText(detailsParceableObject.getTitle());
        published_date.setText(detailsParceableObject.getPublishedDate());
        sub_text.setText(detailsParceableObject.getSubText());
        byLine.setText(detailsParceableObject.getByLine());
        abstract_value.setText(detailsParceableObject.getJsonAbstarct());
        baseUtlis.loadImageGlide(this, detailsParceableObject.getImage(), image);
        if (sharedPrefs.contains("id_" + detailsParceableObject.getId())) {
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));
        } else {
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
        }
    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.iv_close)) {
            onBackPressed();
        }

    }
}
