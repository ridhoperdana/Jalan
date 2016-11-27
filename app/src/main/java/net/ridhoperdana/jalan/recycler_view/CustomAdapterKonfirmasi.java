package net.ridhoperdana.jalan.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.pojo_class.Results;
import net.ridhoperdana.jalan.pojo_class.Tempat_sementara;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by RIDHO on 11/26/2016.
 */

public class CustomAdapterKonfirmasi extends RecyclerView.Adapter<CustomViewHolderKonfirmasi> {

    ArrayList<Tempat_sementara> list = new ArrayList<>();
    Context context;

    @Override
    public CustomViewHolderKonfirmasi onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_konfirmasi, parent, false);
        CustomViewHolderKonfirmasi holder = new CustomViewHolderKonfirmasi(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolderKonfirmasi holder, final int position) {
        final Tempat_sementara results = list.get(position);
//        holder.textviewNamaTempat.setText(list.get(position).getName().toLowerCase());
//        holder.textviewAlamatTempat.setText(list.get(position).getVicinity());
        holder.textviewNamaTempat.setText(results.getNama_tempat().toLowerCase());
        holder.textviewAlamatTempat.setText(results.getAlamat_tempat());
        holder.tombolHapusKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tempat: ",  results.getNama_tempat() + "dihapus");
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public CustomAdapterKonfirmasi(ArrayList<Tempat_sementara> list, Context context) {
        this.list = list;
        this.context = context;
    }
}
