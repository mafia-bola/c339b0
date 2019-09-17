package com.disporapar.listdesawisata.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.disporapar.listdesawisata.API.WisataFoto;
import com.disporapar.listdesawisata.R;
import com.disporapar.listdesawisata.ViewWisataFoto;

import java.util.ArrayList;

public class AdapterWisataFoto extends RecyclerView.Adapter<AdapterWisataFoto.ViewHolder> {

    Context context;
    ArrayList<WisataFoto> apiWisataFoto;

    public AdapterWisataFoto(Context context, ArrayList<WisataFoto> apiWisataFoto) {
        this.context = context;
        this.apiWisataFoto = apiWisataFoto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_wisata_foto, parent, false);
        return new AdapterWisataFoto.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WisataFoto wisata = apiWisataFoto.get(position);

        PicassoClient.downloadImage(context, wisata.getFoto_wisata(), holder.imageWisata);
        holder.imageWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, ViewWisataFoto.class);
                detail.putExtra("foto_wisata", wisata.getFoto_wisata());
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiWisataFoto.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageWisata;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageWisata = itemView.findViewById(R.id.imageWisata);
        }
    }
}
