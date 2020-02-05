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

import com.disporapar.listdesawisata.API.WisataKegiatan;
import com.disporapar.listdesawisata.R;
import com.disporapar.listdesawisata.ViewKegiatan;

import java.util.ArrayList;

public class AdapterWisataKegiatan extends RecyclerView.Adapter<AdapterWisataKegiatan.ViewHolder> {

    private Context context;
    private ArrayList<WisataKegiatan> apiWisataKegiatan;

    public AdapterWisataKegiatan(Context context, ArrayList<WisataKegiatan> apiWisataKegiatan) {
        this.context = context;
        this.apiWisataKegiatan = apiWisataKegiatan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_wisata_kegiatan, parent, false);
        return new AdapterWisataKegiatan.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WisataKegiatan wisataKegiatan = apiWisataKegiatan.get(position);

        PicassoClient.downloadImage(context, wisataKegiatan.getFoto_atraksi(), holder.imageAtraksi);
        holder.txtNamaAtraksi.setText(wisataKegiatan.getNama_atraksi());
        holder.txtNamaWisata.setText(wisataKegiatan.getNama_wisata());
        holder.imageAtraksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, ViewKegiatan.class);
                detail.putExtra("nama_atraksi", wisataKegiatan.getNama_atraksi());
                detail.putExtra("deskripsi", wisataKegiatan.getDeskripsi());
                detail.putExtra("foto_atraksi", wisataKegiatan.getFoto_atraksi());
                detail.putExtra("nama_wisata", wisataKegiatan.getNama_wisata());
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiWisataKegiatan.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageAtraksi;
        TextView txtNamaAtraksi;
        TextView txtNamaWisata;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageAtraksi = itemView.findViewById(R.id.imageAtraksi);
            txtNamaAtraksi = itemView.findViewById(R.id.txtNamaAtraksi);
            txtNamaWisata = itemView.findViewById(R.id.txtNamaWisata);
        }
    }
}
