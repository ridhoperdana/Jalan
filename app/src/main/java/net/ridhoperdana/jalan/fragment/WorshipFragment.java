package net.ridhoperdana.jalan.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.interface_retrofit.GetPlace;
import net.ridhoperdana.jalan.pojo_class.Results;
import net.ridhoperdana.jalan.pojo_class.Tempat;
import net.ridhoperdana.jalan.recycler_view.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RIDHO on 11/25/2016.
 */

public class WorshipFragment extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    public WorshipFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_daftar_tempat, container, false);
        Double lat, longt;
        lat = Double.parseDouble(this.getArguments().getString("lat"));
        longt = Double.parseDouble(this.getArguments().getString("longt"));
        getRetrofitObject(lat, longt, view);
        return view;
    }

    private void getRetrofitObject(Double lat, Double longt, final View view) {

        final Tempat[] places = new Tempat[1];
        final Tempat[] places2 = new Tempat[1];
        final List<Results> tampung = new ArrayList<>();
        final List<Results> tampung2 = new ArrayList<>();
//        List<Results> finalResult = new ArrayList<>();

        StringBuilder urlbaru2 = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        urlbaru2.append("location=" + lat + "," + longt);
        urlbaru2.append("&radius=" + 5000);
        urlbaru2.append("&types=" + "mosque|church");
        urlbaru2.append("&rankBy=" + "distance");
        urlbaru2.append("&key=" + "AIzaSyBVuRYeAWRZhzeF9c51pOUfAC93iP7FgBE");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridhoperdana.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetPlace service = retrofit.create(GetPlace.class);
        Call<Tempat> call2 = service.getPlaceResponseWorship(urlbaru2.toString());

        call2.enqueue(new Callback<Tempat>() {
            @Override
            public void onResponse(Call<Tempat> call, Response<Tempat> response) {
                places2[0] = response.body();
                for(int i = 0; i< places2[0].getResults().size(); i++)
                {
                    tampung2.add(places2[0].getResults().get(i));
//                    Log.d("List Tempat Ibadah->", tampung2.get(i).getName());
                }
                recyclerView = (RecyclerView)view.findViewById(R.id.rv);
                CustomAdapter adapter = new CustomAdapter(tampung2, getActivity());
                recyclerView.setAdapter(adapter);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Call<Tempat> call, Throwable t) {

            }
        });
    }
}
