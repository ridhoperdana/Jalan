package net.ridhoperdana.jalan.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.activity.PilihSendiriActivity;
import net.ridhoperdana.jalan.pojo_class.Results;
import net.ridhoperdana.jalan.pojo_class.Tempat_sementara;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by RIDHO on 11/24/2016.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{

    List<Results> list = Collections.emptyList();
    Context context;
    public Tempat_sementara tempat;
    private int status=0;

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final Results results = list.get(position);
//        holder.textviewNamaTempat.setText(list.get(position).getName().toLowerCase());
//        holder.textviewAlamatTempat.setText(list.get(position).getVicinity());
        holder.textviewNamaTempat.setText(results.getName().toLowerCase());
        holder.textviewAlamatTempat.setText(results.getVicinity());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(results.isSelected());
        if(!results.isSelected())
        {
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("status tekan 1: ", String.valueOf(results.isSelected()));
                    results.setSelected(true);
                    if(results.isSelected())
                    {
                        tempat = new Tempat_sementara(results.getName(), results.getVicinity(), results.getGeometry().getLocation().getLat(), results.getGeometry().getLocation().getLng());
                        if(context instanceof PilihSendiriActivity)
                        {
                            ((PilihSendiriActivity)context).saveToList(tempat);
                        }
                    }
                    Log.d("status tekan 2: ", String.valueOf(results.isSelected()));
                }
            });
        }
        if(results.isSelected())
        {
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    results.setSelected(false);
                    Log.d("status tekan: ", String.valueOf(results.isSelected()));
                }
            });
        }

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
