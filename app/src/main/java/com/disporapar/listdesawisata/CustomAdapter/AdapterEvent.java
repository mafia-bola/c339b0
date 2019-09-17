package com.disporapar.listdesawisata.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.disporapar.listdesawisata.API.Event;
import com.disporapar.listdesawisata.R;
import com.disporapar.listdesawisata.ViewEvent;

import java.util.ArrayList;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolder> {

    Context context;
    ArrayList<Event> apiEvent;

    public AdapterEvent(Context context, ArrayList<Event> apiEvent) {
        this.context = context;
        this.apiEvent = apiEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_event, parent, false);
        return new AdapterEvent.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Event event = apiEvent.get(position);

        PicassoClient.downloadImage(context, event.getFoto_event(), holder.imageEvent);
        holder.imageEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, ViewEvent.class);
                detail.putExtra("tanggal_berlaku", event.getTanggal_event());
                detail.putExtra("foto_event", event.getFoto_event());
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiEvent.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageEvent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageEvent = itemView.findViewById(R.id.imageEvent);
        }
    }
}
