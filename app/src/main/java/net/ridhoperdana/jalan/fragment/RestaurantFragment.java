package net.ridhoperdana.jalan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.activity.PilihSendiriActivity;
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

public class RestaurantFragment extends android.support.v4.app.Fragment{

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    public Tempat places;

    public RestaurantFragment() {
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

//        final Tempat[] places = new Tempat[1];
//        final Tempat[] places2 = new Tempat[1];
        places = new Tempat();
        final List<Results> tampung = new ArrayList<>();
//        List<Results> finalResult = new ArrayList<>();

        StringBuilder urlbaru = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        urlbaru.append("location=" + lat + "," + longt);
        urlbaru.append("&radius=" + 5000);
        urlbaru.append("&types=" + "restaurant|cafe|bar|bakery");
        urlbaru.append("&rankBy=" + "distance");
        urlbaru.append("&key=" + "AIzaSyBVuRYeAWRZhzeF9c51pOUfAC93iP7FgBE");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridhoperdana.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetPlace service = retrofit.create(GetPlace.class);

        Call<Tempat> call = service.getPlaceResponseFood(urlbaru.toString());

        call.enqueue(new Callback<Tempat>() {

            @Override
            public void onResponse(Call<Tempat> call, Response<Tempat> response) {
                places = response.body();
                for(int i = 0; i< places.getResults().size(); i++)
                {
                    tampung.add(places.getResults().get(i));
//                    Log.d("List Nama Restaurant->", tampung.get(i).getName());
                }
                recyclerView = (RecyclerView)view.findViewById(R.id.rv);
                CustomAdapter adapter = new CustomAdapter(tampung, getActivity());
                recyclerView.setAdapter(adapter);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Call<Tempat> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }
}
