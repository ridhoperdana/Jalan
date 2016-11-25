package net.ridhoperdana.jalan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by RIDHO on 11/25/2016.
 */

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView textviewNamaTempat, textviewAlamatTempat;
    View container;

    public CustomViewHolder(View itemView) {
        super(itemView);
        textviewNamaTempat = (TextView)itemView.findViewById(R.id.nama_tempat);
        textviewAlamatTempat = (TextView)itemView.findViewById(R.id.alamat_tempat);
        container = itemView.findViewById(R.id.container_recycler_view);
    }
}
