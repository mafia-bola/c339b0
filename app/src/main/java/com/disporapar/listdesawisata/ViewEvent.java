package com.disporapar.listdesawisata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.disporapar.listdesawisata.CustomAdapter.PicassoClient;

public class ViewEvent extends AppCompatActivity {

    ImageView imageEvent;
    TextView txtNamaEvent, txtDeskripsiEvent, txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);

        imageEvent = findViewById(R.id.imageEvent);
        txtNamaEvent = findViewById(R.id.txtNamaEvent);
        txtDeskripsiEvent = findViewById(R.id.txtDeskripsiEvent);
        txtStatus = findViewById(R.id.txtStatus);

        Intent event = getIntent();
        final String nama_event = event.getStringExtra("judul_event");
        final String deskripsi_event = event.getStringExtra("deskripsi");
        final String foto_event = event.getStringExtra("foto_event");
        final String status = event.getStringExtra("status");

        txtNamaEvent.setText(nama_event);
        txtStatus.setText(status);
        PicassoClient.downloadImage(getApplicationContext(), foto_event, imageEvent);
        txtDeskripsiEvent.setText(deskripsi_event);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtDeskripsiEvent.setText(Html.fromHtml(deskripsi_event, Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtDeskripsiEvent.setText(Html.fromHtml(deskripsi_event));
        }
    }
}
