package net.ridhoperdana.jalan.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.ridhoperdana.jalan.R;

/**
 * Created by RIDHO on 11/26/2016.
 */

public class CustomViewHolderKonfirmasi extends RecyclerView.ViewHolder{

    TextView textviewNamaTempat, textviewAlamatTempat;
    View container;
    Button tombolHapusKonfirmasi;

    public CustomViewHolderKonfirmasi(View itemView) {
        super(itemView);
        textviewNamaTempat = (TextView)itemView.findViewById(R.id.nama_tempat_konfirmasi);
        textviewAlamatTempat = (TextView)itemView.findViewById(R.id.alamat_tempat_konfirmasi);
        container = itemView.findViewById(R.id.container_recycler_view_konfirmasi);
        tombolHapusKonfirmasi = (Button) itemView.findViewById(R.id.tombol_hapus_konfirmasi);
    }
}
