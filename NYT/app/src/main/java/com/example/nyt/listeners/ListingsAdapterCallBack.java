package com.example.nyt.listeners;

import android.widget.ImageView;

public interface ListingsAdapterCallBack {

    void onLoveClickCallback(int position, ImageView love);

    void onListingClickCallback(int position);

}
