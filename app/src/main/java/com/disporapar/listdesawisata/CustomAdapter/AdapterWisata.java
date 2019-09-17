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

import com.disporapar.listdesawisata.API.Wisata;
import com.disporapar.listdesawisata.R;
import com.disporapar.listdesawisata.ViewWisata;

import java.util.ArrayList;

public class AdapterWisata extends RecyclerView.Adapter<AdapterWisata.ViewHolder> {

    Context context;
    ArrayList<Wisata> apiWisata;

    public AdapterWisata(Context context, ArrayList<Wisata> apiWisata) {
        this.context = context;
        this.apiWisata = apiWisata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_wisata, parent, false);
        return new AdapterWisata.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Wisata wisata = apiWisata.get(position);

        PicassoClient.downloadImage(context, wisata.getThumbnail(), holder.imageWisata);
        holder.txtNamaWisata.setText(wisata.getNama_wisata());
        holder.imageWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, ViewWisata.class);
                detail.putExtra("id_wisata", wisata.getId_wisata());
                detail.putExtra("nama_wisata", wisata.getNama_wisata());
                detail.putExtra("alamat_wisata", wisata.getAlamat_wisata());
                detail.putExtra("sejarah", wisata.getSejarah_desa());
                detail.putExtra("demografi", wisata.getDemografi());
                detail.putExtra("potensi", wisata.getPotensi());
                detail.putExtra("thumbnail", wisata.getThumbnail());
                detail.putExtra("lat", wisata.getLat());
                detail.putExtra("lng", wisata.getLng());
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiWisata.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageWisata;
        TextView txtNamaWisata;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageWisata = itemView.findViewById(R.id.imageWisata);
            txtNamaWisata = itemView.findViewById(R.id.txtNamaWisata);
        }
    }
}
