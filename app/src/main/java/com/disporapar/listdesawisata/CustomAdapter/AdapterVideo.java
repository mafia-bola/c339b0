package com.disporapar.listdesawisata.CustomAdapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.disporapar.listdesawisata.API.WisataVideo;
import com.disporapar.listdesawisata.R;

import java.util.ArrayList;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.ViewHolder> {

    private Context context;
    private ArrayList<WisataVideo> apiWisataVideo;
    private int index = 0;

    public AdapterVideo(Context context, ArrayList<WisataVideo> apiWisataVideo) {
        this.context = context;
        this.apiWisataVideo = apiWisataVideo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_wisata_video, parent, false);
        return new AdapterVideo.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final WisataVideo wisataVideo = apiWisataVideo.get(position);

        final MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.videoView);
        holder.videoView.setMediaController(mediaController);

        holder.videoView.setVideoPath(wisataVideo.getVideo_wisata());
        holder.videoView.requestFocus();
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        holder.videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(holder.videoView);

                    }
                });
            }
        });

        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(context, "Video telah berakhir", Toast.LENGTH_SHORT).show();
                if (index++ == apiWisataVideo.size()){
                    index = 0;
                    Toast.makeText(context, "Videos telah selesai", Toast.LENGTH_SHORT).show();
                } else {
                    holder.videoView.setVideoPath(wisataVideo.getVideo_wisata());
                    holder.videoView.start();
                }
            }
        });

        holder.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("API_Test", "Checking" + what + " extra " + extra);
                return false;
            }
        });

        holder.videoView.start();
    }

    @Override
    public int getItemCount() {
        return apiWisataVideo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoWisata);

        }
    }
}
