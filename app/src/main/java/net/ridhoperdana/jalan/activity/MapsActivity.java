package net.ridhoperdana.jalan.activity;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.pojo_class.Tempat_sementara;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private Polyline polyline;
    Context context;
    ArrayList<String> ruteMapTerpendek;
    ArrayList<Tempat_sementara> daftarTempat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);
        context = this;

        ruteMapTerpendek = new ArrayList<>();
        daftarTempat = new ArrayList<>();

        ruteMapTerpendek = getIntent().getStringArrayListExtra("ruteMap");
        daftarTempat = getIntent().getParcelableArrayListExtra("tempatTujuan");



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        for(int i = 0 ; i < ruteMapTerpendek.size(); i++){
            drawPath(ruteMapTerpendek.get(i));
        }

        LatLng location1 = new LatLng(daftarTempat.get(0).getLat_tempat(),daftarTempat.get(0).getLongt_tempat());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1,15));
        for(int i = 0 ; i < daftarTempat.size(); i++){
            LatLng location = new LatLng(daftarTempat.get(i).getLat_tempat(),daftarTempat.get(i).getLongt_tempat());
            if(i==(daftarTempat.size()-1)){
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location).title(daftarTempat.get(i).getNama_tempat()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE) ));
                marker.showInfoWindow();
            }
            else
            {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location).title(daftarTempat.get(i).getNama_tempat()).snippet("Tujuan ke-"+(i+1)));
            }

        }

    }

    public void drawPath(String result) {
//        if (polyline!= null) {
//            googleMap.clear();
//        }
        try {
            String encodedString = result;
            List<LatLng> list = decodePoly(encodedString);

            Random random = new Random();
            int max =230;
            int min = 1;
            int redValue = random.nextInt(max - min + 1) + min;
            int greenValue = random.nextInt(max - min + 1) + min;
            int blueValue = random.nextInt(max - min + 1) + min;

            for (int z = 0; z < list.size() - 1; z++) {
                LatLng src = list.get(z);
                LatLng dest = list.get(z + 1);

                polyline= googleMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(10).color(Color.rgb(redValue,greenValue,blueValue)).geodesic(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
