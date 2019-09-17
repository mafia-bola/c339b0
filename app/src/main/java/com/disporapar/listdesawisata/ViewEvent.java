package com.disporapar.listdesawisata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.disporapar.listdesawisata.CustomAdapter.PicassoClient;

public class ViewEvent extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);

        Intent event = getIntent();
        final String tanggal = event.getStringExtra("tanggal_berlaku");
        final String foto_event = event.getStringExtra("foto_event");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tanggal);

        imageView = findViewById(R.id.imageEvent);
        PicassoClient.downloadImage(getApplicationContext(), foto_event, imageView);
    }
}
