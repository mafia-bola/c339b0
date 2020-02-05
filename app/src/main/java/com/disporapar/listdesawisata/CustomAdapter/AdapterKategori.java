package com.disporapar.listdesawisata.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.disporapar.listdesawisata.API.Kategori;
import com.disporapar.listdesawisata.R;

import java.util.ArrayList;

public class AdapterKategori extends BaseAdapter {

    Context context;
    ArrayList<Kategori> apiKategori;

    public AdapterKategori(Context context, ArrayList<Kategori> apiKategori) {
        this.context = context;
        this.apiKategori = apiKategori;
    }

    @Override
    public int getCount() {
        return apiKategori.size();
    }

    @Override
    public Object getItem(int position) {
        return apiKategori.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spin_kegiatan, parent, false);
        }

        TextView txtNamaKategori = convertView.findViewById(R.id.txtNamaKategori);

        final Kategori kategori = (Kategori) this.getItem(position);
        txtNamaKategori.setText(kategori.getNama_kategori());

        return convertView;
    }
}
