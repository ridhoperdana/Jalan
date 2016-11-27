package net.ridhoperdana.jalan.activity;

import android.content.Intent;
import android.location.*;
import android.location.Location;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.adapter_fragment.AdapterRuteTerpendek;
import net.ridhoperdana.jalan.interface_retrofit.GetPlace;
import net.ridhoperdana.jalan.pojo_class.Tempat;
import net.ridhoperdana.jalan.pojo_class.Tempat_sementara;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenghitungJalanActivity extends AppCompatActivity {

    Double[][] jarak;
    Double jarakTerdekat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menghitung_jalan);
        //Log.d("hasil",permutationFinder("abc").toString());
        //ArrayList<Tempat_sementara> pilihan = getIntent().getParcelableArrayListExtra("pilihan");

//        String lat = getIntent().getStringExtra("lat");
//        String longt = getIntent().getStringExtra("longt");

        ArrayList<Tempat_sementara> pilihan = new ArrayList<>();
        pilihan.add(new Tempat_sementara("ITS","Sukolilo",-7.2768149,112.7900193));
        pilihan.add(new Tempat_sementara("Galaxy Mall","Sukolilo",-7.2753914,112.7798674));
        pilihan.add(new Tempat_sementara("TP","Sukolilo",-7.2629282,112.7381695));
        pilihan.add(new Tempat_sementara("Hitech","Sukolilo",-7.252381,112.7481893));
        pilihan.add(new Tempat_sementara("Unair","Sukolilo",-7.2716127,112.7557165));

        String lat = "-7.2899644";
        String longt = "112.7768827";

        jarak = new Double[pilihan.size()][pilihan.size()];

        Location start = new Location("posisiAwal");
        start.setLatitude(Double.parseDouble(lat));
        start.setLongitude(Double.parseDouble(longt));

        List<Integer> indexRuteTerpendek = getShortestPath(start,pilihan);
        ArrayList<Tempat_sementara> urutanTempat = new ArrayList<>();

        for(int i = 0 ; i < indexRuteTerpendek.size(); i++){
            urutanTempat.add(pilihan.get(indexRuteTerpendek.get(i)));
            System.out.print(indexRuteTerpendek.get(i)+" ");
        }
        System.out.println("\n jarak:"+jarakTerdekat);

        ListView listRute = (ListView)findViewById(R.id.listRuteTerdekat);
        AdapterRuteTerpendek adapter = new AdapterRuteTerpendek(this, R.layout.item_rute,urutanTempat);
        listRute.setAdapter(adapter);

    }

    private List<Integer> getShortestPath(Location lokasiSekarang, ArrayList<Tempat_sementara> pilihan){
//        ArrayList<Tempat_sementara> pilihan = new ArrayList<>();
//        pilihan.add(new Tempat_sementara("ITS","Sukolilo",-7.2768149,112.7900193));
//        pilihan.add(new Tempat_sementara("Galaxy Mall","Sukolilo",-7.2753914,112.7798674));
//        pilihan.add(new Tempat_sementara("TP","Sukolilo",-7.2629282,112.7381695));
//        pilihan.add(new Tempat_sementara("Hitech","Sukolilo",-7.252381,112.7481893));
//        pilihan.add(new Tempat_sementara("Unair","Sukolilo",-7.2716127,112.7557165));

        List<Integer> intList = new ArrayList<>();
        for(int i = 0 ; i < pilihan.size(); i++){
            intList.add(i);
            for (int b = 0; b < pilihan.size(); b++){
                if(i == b){
                    jarak[i][b] = 0.0;
                }
                else {
                    Location lokasiAsal = new Location(pilihan.get(i).getAlamat_tempat());
                    Location lokasiTujuan= new Location(pilihan.get(b).getAlamat_tempat());

                    lokasiAsal.setLatitude(pilihan.get(i).getLat_tempat());
                    lokasiAsal.setLongitude(pilihan.get(i).getLongt_tempat());

                    lokasiTujuan.setLatitude(pilihan.get(b).getLat_tempat());
                    lokasiTujuan.setLongitude(pilihan.get(b).getLongt_tempat());

                    jarak[i][b] = getDistance(lokasiAsal,lokasiTujuan);
                    //Log.d("jaraknya", jarak[i][b]+"");

                }
                System.out.print(jarak[i][b]+"====");

            }
            System.out.println("");
        }


        List<List<Integer>> myLists = listPermutations(intList);
        List<Integer> ruteTerpendek = null;
        jarakTerdekat = 1000000.0;
        String altoString = new String(" ");

        for (List<Integer> al : myLists) {

            double jarakSekarang = 0.0;

            int x = 0;
            Location tempatStart = new Location(pilihan.get(0).getNama_tempat());
            tempatStart.setLatitude(pilihan.get(0).getLat_tempat());
            tempatStart.setLongitude(pilihan.get(0).getLongt_tempat());
            jarakSekarang += getDistance(lokasiSekarang,tempatStart);
            for (; x < al.size()-1; x ++){
                jarakSekarang += jarak[al.get(x)][al.get(x+1)];
                altoString = altoString + pilihan.get(al.get(x));
//                System.out.print(al.get(x));
            }
            altoString = altoString + pilihan.get(al.get(x)).getNama_tempat()+ " ";

            if(jarakSekarang < jarakTerdekat){
                jarakTerdekat = jarakSekarang;
                ruteTerpendek = al;
            }
            System.out.println(jarakSekarang+"");
//            System.out.println();
        }

        return  ruteTerpendek;
    }

    private Double getDistance(Location asal, Location tujuan){

        if(asal!=null&&tujuan!=null){
            //Log.d("jarak", asal.getLatitude()+" "+asal.getLongitude()+" tujuan "+tujuan.getLatitude()+" "+tujuan.getLongitude());
            Double distance = (double)asal.distanceTo(tujuan)/1000;

            return distance;
        }
        else {
            return -1.0;
        }

    }

    public static List<List<Integer>> listPermutations(List<Integer> list) {

        if (list.size() == 0) {
            List<List<Integer>> result = new ArrayList<>();
            result.add(new ArrayList<Integer>());
            return result;
        }

        List<List<Integer>> returnMe = new ArrayList<>();

        Integer firstElement = list.remove(0);

        List<List<Integer>> recursiveReturn = listPermutations(list);
        for (List<Integer> li : recursiveReturn) {

            for (int index = 0; index <= li.size(); index++) {
                List<Integer> temp = new ArrayList<>(li);
                temp.add(index, firstElement);
                returnMe.add(temp);
            }

        }
        return returnMe;
    }

    private Double getRouteDistance(Location asal, Location tujuan){

        Log.d("location",asal.getLatitude()+" "+asal.getLongitude());
        StringBuilder urlbaru = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");

        urlbaru.append("origin="+asal.getLatitude()+","+asal.getLongitude());
        urlbaru.append("&destination="+tujuan.getLatitude()+","+tujuan.getLongitude());
        urlbaru.append("&key=AIzaSyByOHFeEaMV2y6L3VMbItsg2Qeww0iq_As");

        Log.d("urlSekarang", urlbaru.toString());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridhoperdana.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetPlace service = retrofit.create(GetPlace.class);
        Call<JsonElement> getDistanceCall = service.getDistance2Location(urlbaru.toString());

        final Double[] jarakDirection = new Double[1];
        getDistanceCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("routes");
                JsonObject distance = jsonArray.get(0).getAsJsonObject().getAsJsonArray("legs").get(0).getAsJsonObject();
                JsonObject text = distance.getAsJsonObject("distance");


//                jarakDirection[0] = jsonObject.getAsJsonArray("routes").get(1).getAsJsonObject().getAsJsonArray("legs").get(0).getAsJsonObject().get("distance").getAsJsonObject().get("text").toString();

                Log.d("jarakroute",Double.parseDouble(text.get("text").getAsString().split(" ")[0])+"");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("distance result ", "Failed: " + t.getMessage());
            }


        });

        return jarakDirection[0];
    }

}
