package net.ridhoperdana.jalan;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PilihSendiriActivity extends BaseActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_sendiri);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        Double lat, longt;
        lat = Double.parseDouble(bundle.getString("lat"));
        longt = Double.parseDouble(bundle.getString("long"));
        Toast.makeText(this, "Lat: " + lat.toString() + "Long: " + longt.toString(), Toast.LENGTH_SHORT).show();
        getRetrofitObject(lat,longt);
    }

    private void getRetrofitObject(Double lat, Double longt) {

        final Tempat[] places = new Tempat[1];
        final Tempat[] places2 = new Tempat[1];
        final List<Results> tampung = new ArrayList<>();
        final List<Results> tampung2 = new ArrayList<>();
//        List<Results> finalResult = new ArrayList<>();

        StringBuilder urlbaru = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        urlbaru.append("location=" + lat + "," + longt);
        urlbaru.append("&radius=" + 5000);
        urlbaru.append("&types=" + "restaurant|cafe|bar|bakery");
        urlbaru.append("&rankBy=" + "distance");
        urlbaru.append("&key=" + "AIzaSyBVuRYeAWRZhzeF9c51pOUfAC93iP7FgBE");

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

        Call<Tempat> call = service.getPlaceResponseFood(urlbaru.toString());
        Call<Tempat> call2 = service.getPlaceResponseWorship(urlbaru2.toString());

        call.enqueue(new Callback<Tempat>() {

            @Override
            public void onResponse(Call<Tempat> call, Response<Tempat> response) {
                places[0] = response.body();
                for(int i = 0; i< places[0].getResults().size(); i++)
                {
                    tampung.add(places[0].getResults().get(i));
                    Log.d("List Nama Restaurant->", tampung.get(i).getName());
                }
                recyclerView = (RecyclerView)findViewById(R.id.rv);
                CustomAdapter adapter = new CustomAdapter(tampung, getApplication());
                recyclerView.setAdapter(adapter);
                linearLayoutManager = new LinearLayoutManager(PilihSendiriActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Call<Tempat> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

        call2.enqueue(new Callback<Tempat>() {
            @Override
            public void onResponse(Call<Tempat> call, Response<Tempat> response) {
                places2[0] = response.body();
                for(int i = 0; i< places2[0].getResults().size(); i++)
                {
                    tampung2.add(places2[0].getResults().get(i));
                    Log.d("List Tempat Ibadah->", tampung2.get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<Tempat> call, Throwable t) {

            }
        });

        Log.d("tampung: ", String.valueOf(tampung.size()));
        Log.d("tampung2: ", String.valueOf(tampung2.size()));
    }
}
