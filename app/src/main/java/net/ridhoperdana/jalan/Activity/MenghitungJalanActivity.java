package net.ridhoperdana.jalan.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.Session.SessionManager;
import net.ridhoperdana.jalan.adapter_fragment.AdapterRuteTerpendek;
import net.ridhoperdana.jalan.drawer.BaseActivity;
import net.ridhoperdana.jalan.interface_retrofit.GetPlace;
import net.ridhoperdana.jalan.pojo_class.Tempat;
import net.ridhoperdana.jalan.pojo_class.Tempat_sementara;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenghitungJalanActivity extends BaseActivity {

    Double[][] jarak;
    String[][]ruteSeluruh;
    String[]ruteAwal;
    Double jarakTerdekat;
    Context context;
    ArrayList<Tempat_sementara> pilihan;
    Location start;
    Double[]jarakAwal;
    ArrayList<Tempat_sementara> urutanTempat;

    //hasil akhir
    ArrayList<String> ruteMapTerpendek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menghitung_jalan);
        context = this;
        pilihan = getIntent().getParcelableArrayListExtra("pilihan");

        jarak = new Double[pilihan.size()][pilihan.size()];
        jarakAwal = new Double[pilihan.size()];
        ruteAwal = new String[pilihan.size()];
        ruteSeluruh = new String[pilihan.size()][pilihan.size()];


        JSONArray json = new JSONArray();
        for(int i = 0; i < pilihan.size();i++){
            json.put(pilihan.get(i).getJsonObject());
        }

        BackgroundAsynctask backgroundAsynctask = new BackgroundAsynctask(json.toString());
        backgroundAsynctask.execute();
        SessionManager sessionManager = new SessionManager(getApplicationContext());


        start = new Location("posisiAwal");
        start.setLatitude(sessionManager.getLatitude());
        start.setLongitude(sessionManager.getLongitude());



    }

    public void gotoMap(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putStringArrayListExtra("ruteMap",ruteMapTerpendek);
        urutanTempat.add(new Tempat_sementara("lokasi anda","",start.getLatitude(),start.getLongitude()));
        intent.putParcelableArrayListExtra("tempatTujuan",urutanTempat);
        startActivity(intent);
    }

    private List<Integer> getShortestPath(ArrayList<Tempat_sementara> pilihan){

        List<Integer> intList = new ArrayList<>();
        for(int i = 0 ; i < pilihan.size(); i++){
            intList.add(i);
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
            //System.out.println("jarak awal ke "+al.get(0)+" "+jarakAwal[al.get(0)]);

            jarakSekarang += jarakAwal[al.get(0)];
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
            //System.out.println("jarakSekarang "+jarakSekarang+"");
//            System.out.println();
        }

        return  ruteTerpendek;
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

    public void perjalananSelesai(View view){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public String getDistanceFromApi(String dataLokasi){
        HttpURLConnection httpURLConnection;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL("http://ridhoperdana.net/callApi.php");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("location","UTF-8")+"="+URLEncoder.encode(dataLokasi,"UTF-8")+"&"+
                    URLEncoder.encode("startLocation","UTF-8")+"="+URLEncoder.encode(start.getLatitude()+","+start.getLongitude(),"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);

            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return "gagal";
    }

    private void convertToArray(String data){

        //convert jarak
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray1 = jsonObject.getJSONArray("jarak");
            for (int i = 0 ; i <jsonArray1.length();i++){
                JSONArray jsonArray2 = jsonArray1.getJSONArray(i);
                for (int j = 0; j<jsonArray2.length();j++){
                    jarak[i][j]=Double.parseDouble(jsonArray2.get(j).toString());
                    //System.out.println("jarak "+i+j+" "+jarak[i][j]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //convert rute antar tempat
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray1 = jsonObject.getJSONArray("rute");
            for (int i = 0 ; i <jsonArray1.length();i++){
                JSONArray jsonArray2 = jsonArray1.getJSONArray(i);
                for (int j = 0; j<jsonArray2.length();j++){
                    ruteSeluruh[i][j]=jsonArray2.get(j).toString();
//                    System.out.println("rute Seluruh "+i+j+" "+ruteSeluruh[i][j]);
                }
            }
            //System.out.println("rute Seluruh "+jsonArray1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //convert awal ke lokasi
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray1 = jsonObject.getJSONArray("jarakAwal");
            for (int i = 0 ; i <jsonArray1.length();i++){
                jarakAwal[i]=Double.parseDouble(jsonArray1.get(i).toString());
  //              System.out.println("jarak awal adalah "+jarakAwal[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //convert awal ke lokasi
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray1 = jsonObject.getJSONArray("ruteAwal");
            for (int i = 0 ; i <jsonArray1.length();i++){
                ruteAwal[i]=jsonArray1.get(i).toString();
                System.out.println("rute awal adalah "+ruteAwal[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class BackgroundAsynctask extends AsyncTask<String, String,String> {

        String data;
        ProgressDialog progressDialog;

        public BackgroundAsynctask(String dataJson){
            progressDialog = new ProgressDialog(context);
            data = dataJson;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Menghubungkan ke server...");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            return getDistanceFromApi(data);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s.equals("gagal")){
                Toast.makeText(context, "gagal mendapatkan data", Toast.LENGTH_SHORT).show();
            }
            else {
                System.out.println("data dari api "+s);
                convertToArray(s);

                List<Integer> indexRuteTerpendek = getShortestPath(pilihan);
                ruteMapTerpendek = new ArrayList<>();
                urutanTempat = new ArrayList<>();

                ruteMapTerpendek.add(ruteAwal[indexRuteTerpendek.get(0)]);
                System.out.println("rute start ke "+indexRuteTerpendek.get(0)+" -> "+ruteAwal[indexRuteTerpendek.get(0)]);

                for(int i = 0 ; i < indexRuteTerpendek.size(); i++){
                    urutanTempat.add(pilihan.get(indexRuteTerpendek.get(i)));
                    if(i<(indexRuteTerpendek.size()-1)){
                        ruteMapTerpendek.add(ruteSeluruh[indexRuteTerpendek.get(i)][indexRuteTerpendek.get(i+1)]);
                        System.out.println("rute terpendek "+indexRuteTerpendek.get(i)+" "+indexRuteTerpendek.get(i+1));
                        System.out.println(ruteSeluruh[indexRuteTerpendek.get(i)][indexRuteTerpendek.get(i+1)]+"");
                    }
                }

                System.out.println("\n jarak:"+jarakTerdekat);


                ListView listRute = (ListView)findViewById(R.id.listRuteTerdekat);
                AdapterRuteTerpendek adapter = new AdapterRuteTerpendek(context, R.layout.item_rute,urutanTempat);
                listRute.setAdapter(adapter);
            }
        }
    }

}
