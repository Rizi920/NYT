package com.example.nyt.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyt.R;
import com.example.nyt.adapters.ListingsAdapter;
import com.example.nyt.listeners.ListingsAdapterCallBack;
import com.example.nyt.listeners.ListingsDataSuccess;
import com.example.nyt.models.AllListings.ListingResponse;
import com.example.nyt.models.AllListings.ResultsItem;
import com.example.nyt.network.ApiClient;
import com.example.nyt.network.ApiServices;
import com.example.nyt.ui.activities.DetailsActivity;
import com.example.nyt.utils.BaseUtlis;
import com.example.nyt.utils.DetailsParceableObject;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllListingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllListingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllListingsFragment extends Fragment implements ListingsDataSuccess, ListingsAdapterCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "listingObject";
    private static final String NYT_API_KEY = "ZaH04B0I3NNnaJCaycEM3df5adEGQpaF";
    private static final String LINK = "link";

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    ListingsAdapter listingsAdapter;
    ApiServices apiInterface;
    Dialog mProgressDialog;
    ListingResponse listingResponse;
    BaseUtlis baseUtlis;
    TextView no_internet;
    TextView no_internet_explain;
    ImageView no_interneted;

    private String mParam1;
    private String mParam2;
    View mView;

    private OnFragmentInteractionListener mListener;

    public AllListingsFragment() {
        // Required empty public constructor
    }


    public static AllListingsFragment newInstance(String param1, String param2) {
        AllListingsFragment fragment = new AllListingsFragment();
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
        mView = inflater.inflate(R.layout.fragment_all_listings, container, false);
        init();
        SwipeRefreshLayout pullToRefresh = mView.findViewById(R.id.refresh);
        pullToRefresh.setOnRefreshListener(() -> {
            if (baseUtlis.checkWifiConnection(getContext())) {
                getListingsData(this);
                pullToRefresh.setRefreshing(false);
            } else {
            }
        });

        if (baseUtlis.checkWifiConnection(getContext())) {
            no_internet.setVisibility(View.INVISIBLE);
            no_internet_explain.setVisibility(View.INVISIBLE);
            no_interneted.setVisibility(View.INVISIBLE);
            getListingsData(this);
        } else {
            no_internet.setVisibility(View.VISIBLE);
            no_internet_explain.setVisibility(View.VISIBLE);
            no_interneted.setVisibility(View.VISIBLE);
        }
        return mView;
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
    public void onListingDataSuccess(ListingResponse listingResponse) {
        Gson gson = new Gson();
        String json = gson.toJson(listingResponse);
        editor.putString(DETAILS_PARCEABLE_OBJECT, json);
        editor.commit();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> desc = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> image = new ArrayList<>();
        ArrayList<String> id = new ArrayList<>();


        for (int i = 0; i < listingResponse.getResults().size(); i++) {
            ResultsItem obj = listingResponse.getResults().get(i);
            name.add(obj.getTitle());
            desc.add(obj.getSource());
            date.add(obj.getPublishedDate());
            image.add(obj.getMedia().get(0).getMediaMetadata().get(0).getUrl());
            id.add(String.valueOf(obj.getId()));

        }

        RecyclerView recyclerView = mView.findViewById(R.id.rv_for_you);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listingsAdapter = new ListingsAdapter(name, desc, date, image, id, getContext(), this);
        recyclerView.setAdapter(listingsAdapter);
    }


    @Override
    public void onLoveClickCallback(int position, ImageView love) {
        String id = String.valueOf(listingResponse.getResults().get(position).getId());
        if (sharedPrefs.contains("id_" + id)) {
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
            editor.remove("id_" + id);
            editor.apply();
        } else {
            editor.putString("id_" + id, String.valueOf(position));
            editor.apply();
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));
        }
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


    private void init() {
        apiInterface = ApiClient.contactsInfo().create(ApiServices.class);
        no_internet = mView.findViewById(R.id.tv_no_conection);
        no_internet_explain = mView.findViewById(R.id.tv_no_connection_explain);
        no_interneted = mView.findViewById(R.id.iv_no_connection);
        baseUtlis = new BaseUtlis();
        sharedPrefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = sharedPrefs.edit();
    }

    public void getListingsData(ListingsDataSuccess callBack) {
        String Link = sharedPrefs.getString(LINK, "");

        mProgressDialog = baseUtlis.showLoading(getContext());
        apiInterface.getListings(Link, NYT_API_KEY).enqueue(new Callback<ListingResponse>() {
            @Override
            public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                if (response.code() == 200) {
                    listingResponse = response.body();
                    mProgressDialog.dismiss();
                    callBack.onListingDataSuccess(listingResponse);
                }
            }

            @Override
            public void onFailure(Call<ListingResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                t.printStackTrace();
                Log.e("error43", t.getMessage());
                baseUtlis.showErrorDialoge(getContext());

            }
        });

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
