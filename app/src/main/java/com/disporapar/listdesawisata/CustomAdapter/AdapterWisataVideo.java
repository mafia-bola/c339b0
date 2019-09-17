package com.disporapar.listdesawisata.CustomAdapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.disporapar.listdesawisata.API.WisataVideo;
import com.disporapar.listdesawisata.R;

import java.util.ArrayList;

public class AdapterWisataVideo extends BaseAdapter {

    private Context context;
    private ArrayList<WisataVideo> apiWisataVideo;
    private int index = 0;

    public AdapterWisataVideo(Context context, ArrayList<WisataVideo> apiWisataVideo) {
        this.context = context;
        this.apiWisataVideo = apiWisataVideo;
    }

    @Override
    public int getCount() {
        return apiWisataVideo.size();
    }

    @Override
    public Object getItem(int position) {
        return apiWisataVideo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.model_wisata_video, parent, false);
        }

        final VideoView videoView = convertView.findViewById(R.id.videoWisata);
        final WisataVideo wisataVideo = (WisataVideo) this.getItem(position);

        final MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoPath(wisataVideo.getVideo_wisata());
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoView);

                    }
                });
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(context, "Video telah berakhir", Toast.LENGTH_SHORT).show();
                if (index++ == apiWisataVideo.size()){
                    index = 0;
                    Toast.makeText(context, "Videos telah selesai", Toast.LENGTH_SHORT).show();
                } else {
                    videoView.setVideoPath(wisataVideo.getVideo_wisata());
                    videoView.start();
                }
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("API_Test", "Checking" + what + " extra " + extra);
                return false;
            }
        });

        videoView.start();

        return convertView;
    }
}
