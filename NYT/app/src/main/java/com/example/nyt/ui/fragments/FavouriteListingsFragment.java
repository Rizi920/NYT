package com.example.nyt.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyt.R;
import com.example.nyt.adapters.ListingsAdapter;
import com.example.nyt.listeners.ListingsAdapterCallBack;
import com.example.nyt.models.AllListings.ListingResponse;
import com.example.nyt.models.AllListings.ResultsItem;
import com.example.nyt.ui.activities.DetailsActivity;
import com.example.nyt.utils.DetailsParceableObject;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavouriteListingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteListingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteListingsFragment extends Fragment implements ListingsAdapterCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "listingObject";

    ListingsAdapter listingsAdapter;

    ListingResponse listingResponse;
    View mView;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    ArrayList<String> id;


    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public FavouriteListingsFragment() {
        // Required empty public constructor
    }

    public static FavouriteListingsFragment newInstance(String param1, String param2) {
        FavouriteListingsFragment fragment = new FavouriteListingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favourite_listings, container, false);

        return mView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            sharedPrefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            editor = sharedPrefs.edit();
            Gson gson = new Gson();
            String json = sharedPrefs.getString(DETAILS_PARCEABLE_OBJECT, "");
            listingResponse = gson.fromJson(json, ListingResponse.class);
            ImageView no_saved = mView.findViewById(R.id.iv_no_Save);
            TextView no_save = mView.findViewById(R.id.tv_no_save);
            TextView no_save_explain = mView.findViewById(R.id.tv_no_save_explain);
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> desc = new ArrayList<>();
            ArrayList<String> date = new ArrayList<>();
            ArrayList<String> image = new ArrayList<>();
            id = new ArrayList<>();


            for (int i = 0; i < listingResponse.getResults().size(); i++) {
                if (sharedPrefs.contains("id_" + listingResponse.getResults().get(i).getId())) {
                    ResultsItem obj = listingResponse.getResults().get(i);
                    name.add(obj.getTitle());
                    desc.add(obj.getSource());
                    date.add(obj.getPublishedDate());
                    image.add(obj.getMedia().get(0).getMediaMetadata().get(0).getUrl());
                    id.add(String.valueOf(obj.getId()));
                }
            }

            if (id.size() != 0) {
                no_saved.setVisibility(View.INVISIBLE);
                no_save.setVisibility(View.INVISIBLE);
                no_save_explain.setVisibility(View.INVISIBLE);
            } else {
                no_saved.setVisibility(View.VISIBLE);
                no_save.setVisibility(View.VISIBLE);
                no_save_explain.setVisibility(View.VISIBLE);
            }


            RecyclerView recyclerView = mView.findViewById(R.id.rv_saved);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            listingsAdapter = new ListingsAdapter(name, desc, date, image, id, getContext(), this);
            recyclerView.setAdapter(listingsAdapter);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoveClickCallback(int position, ImageView love) {

    }

    @Override
    public void onListingClickCallback(int position) {
        ResultsItem obj = listingResponse.getResults().get(position);
        String title = obj.getTitle();
        String subtext = obj.getSource();
        String publishedDate = obj.getPublishedDate();
        String byLine = obj.getByline();
        String jsonAbstract = obj.getJsonMemberAbstract();
        String image = obj.getMedia().get(0).getMediaMetadata().get(0).getUrl();
        String id = String.valueOf(obj.getId());
        DetailsParceableObject detailsParceableObject = new DetailsParceableObject(title, subtext, publishedDate, jsonAbstract, byLine, id, image);
        Intent myIntent = new Intent(getContext(), DetailsActivity.class);
        myIntent.putExtra(DETAILS_PARCEABLE_OBJECT, detailsParceableObject);
        getActivity().startActivity(myIntent);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
