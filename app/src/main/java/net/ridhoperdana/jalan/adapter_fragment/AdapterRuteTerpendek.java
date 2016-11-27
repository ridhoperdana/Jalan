package net.ridhoperdana.jalan.adapter_fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.pojo_class.Tempat;
import net.ridhoperdana.jalan.pojo_class.Tempat_sementara;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luffi on 27/11/2016.
 */

public class AdapterRuteTerpendek extends ArrayAdapter<Tempat_sementara> {

    private ArrayList<Tempat_sementara> arrayList;
    private  int resource;

    public AdapterRuteTerpendek(Context context, int resource, ArrayList<Tempat_sementara> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Nullable
    @Override
    public Tempat_sementara getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tempat_sementara pilihanSekarang = getItem(position);
        TempatHolder holder ;
        if(convertView==null){
            holder = new TempatHolder();
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            holder.nama = (TextView)convertView.findViewById(R.id.nama_tempat);
            holder.alamat = (TextView)convertView.findViewById(R.id.alamat_tempat);
            holder.navigasi_btn = (Button)convertView.findViewById(R.id.tombolNavigasi);
            holder.nomor = (TextView)convertView.findViewById(R.id.nomor);
            convertView.setTag(holder);
        }
        else {
            holder = (TempatHolder)convertView.getTag();
        }
        holder.nama.setText(pilihanSekarang.getNama_tempat());
        holder.alamat.setText(pilihanSekarang.getAlamat_tempat());
        int posisi = position+1;
        holder.nomor.setText(posisi+"");
        holder.navigasi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+pilihanSekarang.getLat_tempat()+","+pilihanSekarang.getLongt_tempat()));
                getContext().startActivity(intent);

            }
        });

        return convertView;
    }

    private class TempatHolder{
        TextView nama, alamat, nomor;
        Button navigasi_btn;

    }
}
