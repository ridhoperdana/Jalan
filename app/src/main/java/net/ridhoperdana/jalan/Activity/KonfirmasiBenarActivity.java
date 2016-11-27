package net.ridhoperdana.jalan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.pojo_class.Tempat_sementara;
import net.ridhoperdana.jalan.recycler_view.CustomAdapterDipilihkan;
import net.ridhoperdana.jalan.recycler_view.CustomAdapterKonfirmasiBenar;

import java.util.ArrayList;

public class KonfirmasiBenarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;
        recyclerView = (RecyclerView)findViewById(R.id.rv_konfirmasi);

        final ArrayList<Tempat_sementara> getObject;
        getObject = getIntent().getParcelableArrayListExtra("list");
        for(int i=0; i<getObject.size(); i++)
        {
            Log.d("nama: " + getObject.get(i).getNama_tempat(), "Lat: " + getObject.get(i).getLat_tempat());
        }

        CustomAdapterKonfirmasiBenar adapter = new CustomAdapterKonfirmasiBenar(getObject, getApplicationContext());
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
