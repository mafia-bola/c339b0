package com.disporapar.listdesawisata.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.disporapar.listdesawisata.API.Kegiatan;
import com.disporapar.listdesawisata.R;
import com.disporapar.listdesawisata.ViewKegiatan;

import java.util.ArrayList;

public class AdapterKegiatan extends RecyclerView.Adapter<AdapterKegiatan.ViewHolder> {

    Context context;
    ArrayList<Kegiatan> apiKegiatan;

    public AdapterKegiatan(Context context, ArrayList<Kegiatan> apiKegiatan) {
        this.context = context;
        this.apiKegiatan = apiKegiatan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_kegiatan, parent, false);
        return new AdapterKegiatan.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Kegiatan kegiatan = apiKegiatan.get(position);

        PicassoClient.downloadImage(context, kegiatan.getFoto_kegiatan(), holder.imageKegiatan);
        holder.txtNamaKegiatan.setText(kegiatan.getNama_kegiatan());
        holder.imageKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, ViewKegiatan.class);
                detail.putExtra("nama_kegiatan", kegiatan.getNama_kegiatan());
                detail.putExtra("deskripsi", kegiatan.getDeskripsi());
                detail.putExtra("foto_kegiatan", kegiatan.getFoto_kegiatan());
                detail.putExtra("nama_kategori", kegiatan.getNama_kategori());
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiKegiatan.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageKegiatan;
        TextView txtNamaKegiatan;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageKegiatan = itemView.findViewById(R.id.imageKegiatan);
            txtNamaKegiatan = itemView.findViewById(R.id.txtNamaKegiatan);
        }
    }
}
