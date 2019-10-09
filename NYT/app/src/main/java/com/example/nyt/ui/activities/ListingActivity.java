package com.example.nyt.ui.activities;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.nyt.R;
import com.example.nyt.adapters.ListingsAdapter;
import com.example.nyt.adapters.MyPagerAdapter;
import com.example.nyt.listeners.ListingsAdapterCallBack;
import com.example.nyt.models.AllListings.ListingResponse;
import com.example.nyt.models.AllListings.ResultsItem;
import com.example.nyt.ui.fragments.AllListingsFragment;
import com.example.nyt.ui.fragments.FavouriteListingsFragment;
import com.google.gson.Gson;


import java.util.ArrayList;


public class ListingActivity extends AppCompatActivity implements FavouriteListingsFragment.OnFragmentInteractionListener, AllListingsFragment.OnFragmentInteractionListener, View.OnClickListener {

    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "listingObject";
    private static final String TITLE = "title";


    TabLayout tabLayout;
    FragmentPagerAdapter adapterViewPager;
    SearchView searchView;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    TextView no_search_results;
    TextView no_search_results_explain;
    ImageView no_search_result;
    ConstraintLayout hidden_layout;
    RecyclerView hiddenRecyclerview;
    ListingsAdapter listingsAdapter;
    ListingResponse listingResponse;
    TextView title;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        init();
        visibilityCOntroller();
        setupTabLayout();
        View view = this.getCurrentFocus();
        back.setOnClickListener(this);

        title.setText(sharedPrefs.getString(TITLE, ""));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setUpSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setUpSearch(newText);
                return false;
            }
        });
    }


    private void init() {
        no_search_result = findViewById(R.id.iv_no_search_results);
        no_search_results = findViewById(R.id.tv_no_search_results);
        no_search_results_explain = findViewById(R.id.tv_no_search_results_explain);
        hiddenRecyclerview = findViewById(R.id.rv_hidden_search);
        sharedPrefs = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = sharedPrefs.edit();
        title = findViewById(R.id.tv_toolbar_title);
        back = findViewById(R.id.iv_back);
        searchView = findViewById(R.id.sv_search);

    }

    public void visibilityCOntroller() {
        tabLayout = findViewById(R.id.tab_layout);
        hiddenRecyclerview.setVisibility(View.VISIBLE);
        no_search_result.setVisibility(View.INVISIBLE);
        no_search_results.setVisibility(View.INVISIBLE);
        no_search_results_explain.setVisibility(View.INVISIBLE);
    }

    private void setupTabLayout() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        ViewPager vpPager = findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(vpPager);
    }

    private void setUpSearch(String query) {
        if (query.equalsIgnoreCase("")) {
            ConstraintLayout hidden_layout = findViewById(R.id.hidden_constraint_layout);
            hidden_layout.setVisibility(View.INVISIBLE);
            LinearLayout linearLayout = findViewById(R.id.linera_layout_pager);
            linearLayout.setVisibility(View.VISIBLE);
            init();
            setupTabLayout();
        } else {
            hidden_layout = findViewById(R.id.hidden_constraint_layout);
            hidden_layout.setVisibility(View.VISIBLE);
            LinearLayout linearLayout = findViewById(R.id.linera_layout_pager);
            linearLayout.setVisibility(View.INVISIBLE);

            Gson gson = new Gson();
            String json = sharedPrefs.getString(DETAILS_PARCEABLE_OBJECT, "");
            listingResponse = gson.fromJson(json, ListingResponse.class);
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> desc = new ArrayList<>();
            ArrayList<String> date = new ArrayList<>();
            ArrayList<String> image = new ArrayList<>();
            ArrayList<String> id = new ArrayList<>();


            for (int i = 0; i < listingResponse.getResults().size(); i++) {
                ResultsItem obj = listingResponse.getResults().get(i);
                if (obj.getTitle() != null) {
                    if (obj.getTitle().toLowerCase().contains(query)) {
                        name.add(obj.getTitle());
                        desc.add(obj.getSource());
                        date.add(obj.getPublishedDate());
                        image.add(obj.getMedia().get(0).getMediaMetadata().get(0).getUrl());
                        id.add(String.valueOf(obj.getId()));
                    }
                }
            }


            if (id.size() == 0) {
                hiddenRecyclerview.setVisibility(View.INVISIBLE);
                no_search_result.setVisibility(View.VISIBLE);
                no_search_results.setVisibility(View.VISIBLE);
                no_search_results_explain.setVisibility(View.VISIBLE);
            } else {
                visibilityCOntroller();
                RecyclerView recyclerView = findViewById(R.id.rv_hidden_search);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                listingsAdapter = new ListingsAdapter(name, desc, date, image, id, ListingActivity.this, new ListingsAdapterCallBack() {
                    @Override
                    public void onLoveClickCallback(int position, ImageView love) {

                    }

                    @Override
                    public void onListingClickCallback(int position) {

                    }
                });
                recyclerView.setAdapter(listingsAdapter);
            }
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.iv_back)) {
            onBackPressed();
        }
    }


}
