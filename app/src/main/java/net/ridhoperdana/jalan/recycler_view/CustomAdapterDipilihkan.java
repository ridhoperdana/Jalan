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

/**
 * Created by RIDHO on 11/27/2016.
 */

public class CustomAdapterDipilihkan extends RecyclerView.Adapter<CustomViewHolderDipilihkan> {
    ArrayList<Results> list = new ArrayList<>();
    Context context;

    @Override
    public CustomViewHolderDipilihkan onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_dipilihkan, parent, false);
        CustomViewHolderDipilihkan holder = new CustomViewHolderDipilihkan(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolderDipilihkan holder, final int position) {
        final Results results = list.get(position);
//        holder.textviewNamaTempat.setText(list.get(position).getName().toLowerCase());
//        holder.textviewAlamatTempat.setText(list.get(position).getVicinity());
        holder.textviewNamaTempat.setText(results.getName().toLowerCase());
        holder.textviewAlamatTempat.setText(results.getVicinity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public CustomAdapterDipilihkan(ArrayList<Results> list, Context context) {
        this.list = list;
        this.context = context;
    }
}
