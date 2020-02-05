package com.disporapar.listdesawisata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.disporapar.listdesawisata.CustomAdapter.PicassoClient;

public class ViewKegiatan extends AppCompatActivity {

    ImageView imageKegiatan;
    TextView txtNamaKegiatan, txtNamaKategori, txtDeskripsiKegiatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_kegiatan);

        imageKegiatan = findViewById(R.id.imageKegiatan);
        txtNamaKegiatan = findViewById(R.id.txtNamaKegiatan);
        txtNamaKategori = findViewById(R.id.txtNamaKategori);
        txtDeskripsiKegiatan = findViewById(R.id.txtDeskripsiKegiatan);

        Intent atraksi = getIntent();
        final String nama_kegiatan = atraksi.getStringExtra("nama_kegiatan");
        final String deskripsi = atraksi.getStringExtra("deskripsi");
        final String foto_kegiatan = atraksi.getStringExtra("foto_kegiatan");
        final String kategori = atraksi.getStringExtra("nama_kategori");

        txtNamaKegiatan.setText(nama_kegiatan);
        txtNamaKategori.setText(kategori);
        PicassoClient.downloadImage(getApplicationContext(), foto_kegiatan, imageKegiatan);
        txtDeskripsiKegiatan.setText(deskripsi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtDeskripsiKegiatan.setText(Html.fromHtml(deskripsi, Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtDeskripsiKegiatan.setText(Html.fromHtml(deskripsi));
        }
    }
}
