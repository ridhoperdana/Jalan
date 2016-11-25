package net.ridhoperdana.jalan.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.pojo_class.Results;

import java.util.Collections;
import java.util.List;

/**
 * Created by RIDHO on 11/24/2016.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{

    List<Results> list = Collections.emptyList();
    Context context;

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.textviewNamaTempat.setText(list.get(position).getName().toLowerCase());
        holder.textviewAlamatTempat.setText(list.get(position).getVicinity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public CustomAdapter(List<Results> list, Context context) {
        this.list = list;
        this.context = context;
    }
}
