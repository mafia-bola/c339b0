package com.disporapar.listdesawisata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.disporapar.listdesawisata.CustomAdapter.PicassoClient;

public class ViewKalendar extends AppCompatActivity {

    ImageView imageKalendar;
    TextView txtNamaKalendar, txtDeskripsiKalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_kalendar);

        imageKalendar = findViewById(R.id.imageKalendar);
        txtNamaKalendar = findViewById(R.id.txtNamaKalendar);
        txtDeskripsiKalendar = findViewById(R.id.txtDeskripsiKalendar);

        Intent kalendar = getIntent();
        final String nama_kalendar = kalendar.getStringExtra("judul_kalendar");
        final String deskripsi_kalendar = kalendar.getStringExtra("deskripsi");
        final String foto_kalendar = kalendar.getStringExtra("foto_kalendar");

        txtNamaKalendar.setText(nama_kalendar);
        PicassoClient.downloadImage(getApplicationContext(), foto_kalendar, imageKalendar);
        txtDeskripsiKalendar.setText(deskripsi_kalendar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtDeskripsiKalendar.setText(Html.fromHtml(deskripsi_kalendar, Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtDeskripsiKalendar.setText(Html.fromHtml(deskripsi_kalendar));
        }
    }
}
