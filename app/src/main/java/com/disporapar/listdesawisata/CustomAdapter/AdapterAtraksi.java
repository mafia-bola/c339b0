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

import com.disporapar.listdesawisata.API.Atraksi;
import com.disporapar.listdesawisata.R;
import com.disporapar.listdesawisata.ViewAtraksi;

import java.util.ArrayList;

public class AdapterAtraksi extends RecyclerView.Adapter<AdapterAtraksi.ViewHolder> {

    Context context;
    ArrayList<Atraksi> apiAtraksi;

    public AdapterAtraksi(Context context, ArrayList<Atraksi> apiAtraksi) {
        this.context = context;
        this.apiAtraksi = apiAtraksi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_atraksi, parent, false);
        return new AdapterAtraksi.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Atraksi atraksi = apiAtraksi.get(position);

        PicassoClient.downloadImage(context, atraksi.getFoto_atraksi(), holder.imageAtraksi);
        holder.txtNamaAtraksi.setText(atraksi.getNama_atraksi());
        holder.txtNamaWisata.setText(atraksi.getNama_wisata());
        holder.imageAtraksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, ViewAtraksi.class);
                detail.putExtra("nama_atraksi", atraksi.getNama_atraksi());
                detail.putExtra("deskripsi", atraksi.getDeskripsi());
                detail.putExtra("foto_atraksi", atraksi.getFoto_atraksi());
                detail.putExtra("nama_wisata", atraksi.getNama_wisata());
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiAtraksi.size();
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
