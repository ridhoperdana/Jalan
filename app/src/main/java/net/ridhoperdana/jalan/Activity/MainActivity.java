package net.ridhoperdana.jalan.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.ridhoperdana.jalan.drawer.BaseActivity;
import net.ridhoperdana.jalan.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private LocationManager manager;
    private Location mLastLocation;
    private Double lat, longt;
    private String Alamat, Alamat_saatini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button tombolPilihSendiri   = (Button)findViewById(R.id.pilihSendiri);
        Button tombolDipilihkan     = (Button)findViewById(R.id.tombolPilihkan);
        getLocation();
        try {
            getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tombolDipilihkan.setOnClickListener(klik);
        tombolPilihSendiri.setOnClickListener(klik);
        verifyLocationPermissions(this);
    }

    private View.OnClickListener klik = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.pilihSendiri)
            {
                Intent intent = new Intent(MainActivity.this, PilihSendiriActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("lat", lat.toString());
                bundle.putString("long", longt.toString());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
            else if(v.getId()==R.id.tombolPilihkan)
            {
                Intent intent = new Intent(MainActivity.this, DipilihkanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("lat", lat.toString());
                bundle.putString("long", longt.toString());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        }
    };

    private static void verifyLocationPermissions(Activity activity) {
        int permission;
        String[] PERMISSIONS_LOCATION = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        // Check if we have write permission
        permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_LOCATION,
                    1
            );
        }
    }

    private void getLocation()
    {
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try{
            mLastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (mLastLocation == null){
                mLastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(mLastLocation==null)
                {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
                    mLastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
            else if (mLastLocation != null)
                Log.d("Location : ","Lat = "+ mLastLocation.getLatitude() + " Lng");
        }catch (Exception e)
        {
            Log.d("Gagal lokasi terbaru", "fail");
        }
    }

    private int getAddress() throws IOException {
        try{
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<android.location.Address> addresses;
            addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            lat = mLastLocation.getLatitude();
            longt = mLastLocation.getLongitude();

            Alamat = addresses.get(0).getAddressLine(0);
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
//        lokasi_saya = (TextView)findViewById(R.id.lokasi_sekarang);
//        lokasi_saya.setText(Alamat);
            Alamat_saatini = Alamat;
            Toast.makeText(this, "Lokasi sekarang: " + Alamat_saatini, Toast.LENGTH_SHORT).show();
            return 1;
        }catch (Exception e)
        {
            Log.d("Gagal dapat address", "fail");
            return 2;
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
