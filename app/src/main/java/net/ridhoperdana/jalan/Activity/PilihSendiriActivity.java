package net.ridhoperdana.jalan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.ridhoperdana.jalan.fragment.RestaurantFragment;
import net.ridhoperdana.jalan.fragment.ShoppingFragment;
import net.ridhoperdana.jalan.adapter_fragment.ViewPagerAdapter;
import net.ridhoperdana.jalan.fragment.WisataFragment;
import net.ridhoperdana.jalan.fragment.WorshipFragment;
import net.ridhoperdana.jalan.drawer.BaseActivity;
import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.pojo_class.Tempat_sementara;

import java.util.ArrayList;
import java.util.List;

public class PilihSendiriActivity extends BaseActivity {
    ArrayList<Tempat_sementara> list_tempat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_sendiri);
        Button tombolSelesai = (Button)findViewById(R.id.tombolSelesaiPilih);
        ViewPager viewPager;
        TabLayout tabLayout;
        Bundle bundle = getIntent().getBundleExtra("bundle");
        Double lat, longt;
        lat = Double.parseDouble(bundle.getString("lat"));
        longt = Double.parseDouble(bundle.getString("long"));

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(5);
        setupViewPager(viewPager, lat, longt);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        Toast.makeText(this, "Lat: " + lat.toString() + "Long: " + longt.toString(), Toast.LENGTH_SHORT).show();
        tombolSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PilihSendiriActivity.this, KonfirmasiActivity.class);
                Bundle bundle1 = new Bundle();
                intent.putParcelableArrayListExtra("list", list_tempat);
                if(list_tempat.size()==0)
                {
                    Toast.makeText(getApplicationContext(), "Tujuan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    //untuk memasukkan fragment ke adapter
    private void setupViewPager(ViewPager viewPager, Double lat, Double longt) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        RestaurantFragment fragobj = new RestaurantFragment();
        adapter.addFragment(fragobj, "Makanan");
        WorshipFragment fragobj2 = new WorshipFragment();
        adapter.addFragment(fragobj2, "Tempat Ibadah");
        WisataFragment fragobj3 = new WisataFragment();
        adapter.addFragment(fragobj3, "Wisata");
        ShoppingFragment fragobj4 = new ShoppingFragment();
        adapter.addFragment(fragobj4, "Belanja");
        viewPager.setAdapter(adapter);
        Bundle bundle = new Bundle();
        bundle.putString("lat", String.valueOf(lat));
        bundle.putString("longt", String.valueOf(longt));
        fragobj.setArguments(bundle);
        fragobj2.setArguments(bundle);
        fragobj3.setArguments(bundle);
        fragobj4.setArguments(bundle);
    }

    public void saveToList(Tempat_sementara tempat)
    {
        list_tempat.add(tempat);
        for(int i=0; i<list_tempat.size(); i++)
        {
            Log.d("tempat tambah: ", list_tempat.get(i).getNama_tempat());
        }
    }
}
