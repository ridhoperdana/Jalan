package net.ridhoperdana.jalan.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.ridhoperdana.jalan.R;

/**
 * Created by RIDHO on 11/27/2016.
 */

public class CustomViewHolderKonfirmasiBenar extends RecyclerView.ViewHolder{
    TextView textviewNamaTempat, textviewAlamatTempat;
    View container;
    Button tombolNavigasi;

    public CustomViewHolderKonfirmasiBenar(View itemView) {
        super(itemView);
        textviewNamaTempat = (TextView)itemView.findViewById(R.id.nama_tempat_dipilihkan);
        textviewAlamatTempat = (TextView)itemView.findViewById(R.id.alamat_tempat_dipilihkan);
        container = itemView.findViewById(R.id.container_recycler_view_dipilihkan);
    }
}
