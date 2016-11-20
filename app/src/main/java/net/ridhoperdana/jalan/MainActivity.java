package net.ridhoperdana.jalan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button tombolPilihSendiri   = (Button)findViewById(R.id.pilihSendiri);
        Button tombolDipilihkan     = (Button)findViewById(R.id.tombolPilihkan);
        tombolDipilihkan.setOnClickListener(klik);
        tombolPilihSendiri.setOnClickListener(klik);
    }

    private View.OnClickListener klik = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.pilihSendiri)
            {
                startActivity(new Intent(MainActivity.this, PilihSendiriActivity.class));
            }
            if(v.getId()==R.id.tombolPilihkan)
            {
                startActivity(new Intent(MainActivity.this, DipilihkanActivity.class));
            }
        }
    };
}
