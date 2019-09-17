package com.disporapar.listdesawisata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.disporapar.listdesawisata.CustomAdapter.PicassoClient;

public class ViewWisataFoto extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_wisata_foto);

        Intent wisata = getIntent();
        final String foto_wisata = wisata.getStringExtra("foto_wisata");

        imageView = findViewById(R.id.imageWisata);
        PicassoClient.downloadImage(ViewWisataFoto.this, foto_wisata, imageView);
    }
}
