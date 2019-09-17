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

import com.disporapar.listdesawisata.API.Kalendar;
import com.disporapar.listdesawisata.R;
import com.disporapar.listdesawisata.ViewKalendar;

import java.util.ArrayList;

public class AdapterKalendar extends RecyclerView.Adapter<AdapterKalendar.ViewHolder> {

    Context context;
    ArrayList<Kalendar> apiKalendar;

    public AdapterKalendar(Context context, ArrayList<Kalendar> apiKalendar) {
        this.context = context;
        this.apiKalendar = apiKalendar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_kalendar, parent, false);
        return new AdapterKalendar.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Kalendar kalendar = apiKalendar.get(position);

        PicassoClient.downloadImage(context, kalendar.getFoto_kalendar(), holder.imageKalendar);
        holder.txtNamaKalendar.setText(kalendar.getJudul_kalendar());
        holder.imageKalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, ViewKalendar.class);
                detail.putExtra("judul_kalendar", kalendar.getJudul_kalendar());
                detail.putExtra("deskripsi", kalendar.getDeskripsi());
                detail.putExtra("foto_kalendar", kalendar.getFoto_kalendar());
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiKalendar.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageKalendar;
        TextView txtNamaKalendar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageKalendar = itemView.findViewById(R.id.imageKalendar);
            txtNamaKalendar = itemView.findViewById(R.id.txtNamaKalendar);
        }
    }
}
