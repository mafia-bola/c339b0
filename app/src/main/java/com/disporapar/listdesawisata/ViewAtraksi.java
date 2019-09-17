package com.disporapar.listdesawisata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.disporapar.listdesawisata.CustomAdapter.PicassoClient;

public class ViewAtraksi extends AppCompatActivity {

    ImageView imageAtraksi;
    TextView txtNamaAtraksi, txtNamaWisata, txtDeskripsiAtraksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_atraksi);

        imageAtraksi = findViewById(R.id.imageAtraksi);
        txtNamaAtraksi = findViewById(R.id.txtNamaAtraksi);
        txtNamaWisata = findViewById(R.id.txtNamaWisata);
        txtDeskripsiAtraksi = findViewById(R.id.txtDeskripsiAtraksi);

        Intent atraksi = getIntent();
        final String nama_atraksi = atraksi.getStringExtra("nama_atraksi");
        final String deskripsi = atraksi.getStringExtra("deskripsi");
        final String foto_atraksi = atraksi.getStringExtra("foto_atraksi");
        final String nama_wisata = atraksi.getStringExtra("nama_wisata");

        txtNamaAtraksi.setText(nama_atraksi);
        txtNamaWisata.setText(nama_wisata);
        PicassoClient.downloadImage(getApplicationContext(), foto_atraksi, imageAtraksi);
        txtDeskripsiAtraksi.setText(deskripsi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtDeskripsiAtraksi.setText(Html.fromHtml(deskripsi, Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtDeskripsiAtraksi.setText(Html.fromHtml(deskripsi));
        }
    }
}
