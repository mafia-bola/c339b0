package com.disporapar.listdesawisata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.disporapar.listdesawisata.API.Wisata;
import com.disporapar.listdesawisata.API.WisataFoto;
import com.disporapar.listdesawisata.API.WisataKegiatan;
import com.disporapar.listdesawisata.API.WisataVideo;
import com.disporapar.listdesawisata.CustomAdapter.AdapterWisataFoto;
import com.disporapar.listdesawisata.CustomAdapter.AdapterWisataKegiatan;
import com.disporapar.listdesawisata.CustomAdapter.AdapterWisataVideo;
import com.disporapar.listdesawisata.CustomAdapter.CustomDecoration;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ViewWisata extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    RecyclerView listFoto, listKegiatan;
    ListView listVideo;
    AdapterWisataFoto adapterWisataFoto;
    AdapterWisataVideo adapterWisataVideo;
    AdapterWisataKegiatan adapterWisataKegiatan;
    LinearLayoutManager linearLayoutManager;
    ArrayList<WisataFoto> apiWisataFoto = new ArrayList<>();
    ArrayList<WisataVideo> apiWisataVideo = new ArrayList<>();
    ArrayList<WisataKegiatan> apiWisataKegiatan = new ArrayList<>();
    CustomDecoration customDecoration;

    TextView txtNamaWisata, txtAlamatWisata, txtSejarahWisata, txtDemografiWisata, txtPotensiWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_wisata);

        txtNamaWisata = findViewById(R.id.txtNamaWisata);
        txtAlamatWisata = findViewById(R.id.txtAlamatWisata);
        txtSejarahWisata = findViewById(R.id.txtSejarahWisata);
        txtDemografiWisata = findViewById(R.id.txtDemografiWisata);
        txtPotensiWisata = findViewById(R.id.txtPotensiWisata);

        Intent wisata = getIntent();
        final long id_wisata = wisata.getLongExtra("id_wisata",0);
        final String nama_wisata = wisata.getStringExtra("nama_wisata");
        final String alamat_wisata = wisata.getStringExtra("alamat_wisata");
        final String sejarah_wisata = wisata.getStringExtra("sejarah");
        final String demografi = wisata.getStringExtra("demografi");
        final String potensi = wisata.getStringExtra("potensi");

        txtNamaWisata.setText(nama_wisata);
        txtAlamatWisata.setText(alamat_wisata);
        txtSejarahWisata.setText(sejarah_wisata);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtSejarahWisata.setText(Html.fromHtml(sejarah_wisata, Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtSejarahWisata.setText(Html.fromHtml(sejarah_wisata));
        }

        txtDemografiWisata.setText(demografi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtDemografiWisata.setText(Html.fromHtml(demografi, Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtDemografiWisata.setText(Html.fromHtml(demografi));
        }

        txtPotensiWisata.setText(potensi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtPotensiWisata.setText(Html.fromHtml(potensi, Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtPotensiWisata.setText(Html.fromHtml(potensi));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        listFoto = findViewById(R.id.listWisataFoto);
        dataFoto();

        listVideo = findViewById(R.id.listWisataVideo);
        dataVideo();

        listKegiatan = findViewById(R.id.listWisataKegiatan);
        dataAtraksi();

        apiWisataFoto.clear();
        apiWisataVideo.clear();
        apiWisataKegiatan.clear();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent wisata = getIntent();
        final double lat = wisata.getDoubleExtra("lat", 0);
        final double lng = wisata.getDoubleExtra("lng", 0);
        final String nama_wisata = wisata.getStringExtra("nama_wisata");

        mMap = googleMap;

        LatLng location = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(location).title(nama_wisata));

        float zoomLevel = 10.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
    }

    private void dataFoto() {
        final ProgressDialog progressDialog = new ProgressDialog(ViewWisata.this);
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        Intent wisata = getIntent();
        final long id_wisata = wisata.getLongExtra("id_wisata",0);
        String id = String.valueOf(id_wisata);

        final String alamatUrl = getString(R.string.alamatUrl);
        final String wisataUrl = alamatUrl + "api/wisata/" + id + "/foto/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, wisataUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            WisataFoto wisata;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listWisataFoto = response.getJSONObject(i);
                                long id_foto = listWisataFoto.getLong("id");
                                long desa_wisata_id = listWisataFoto.getLong("desa_wisata_id");
                                String foto_wisata = listWisataFoto.getString("file");

                                foto_wisata = alamatUrl+foto_wisata;

                                wisata = new WisataFoto();
                                wisata.setId_foto(id_foto);
                                wisata.setDesa_wisata_id(desa_wisata_id);
                                wisata.setFoto_wisata(foto_wisata);

                                apiWisataFoto.add(wisata);
                            }

                            adapterWisataFoto = new AdapterWisataFoto(getApplicationContext(), apiWisataFoto);
                            linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            customDecoration = new CustomDecoration(10);

                            listFoto.setAdapter(adapterWisataFoto);
                            adapterWisataFoto.notifyDataSetChanged();
                            adapterWisataFoto.notifyItemRemoved(response.length());
                            listFoto.setLayoutManager(linearLayoutManager);
                            progressDialog.dismiss();

                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.e("JSON", e.toString());
                            Toast.makeText(ViewWisata.this, "Data terkendala gangguan teknis, tidak ditemukan atau Tidak terkoneksi", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Volley", error.toString());
                        Toast.makeText(ViewWisata.this, "Server tidak berfungsi atau Non-aktif", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));
        requestQueue.add(jsonArrayRequest);
    }

    private void dataVideo() {
        final ProgressDialog progressDialog = new ProgressDialog(ViewWisata.this);
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        Intent wisata = getIntent();
        final long id_wisata = wisata.getLongExtra("id_wisata",0);
        String id = String.valueOf(id_wisata);

        final String alamatUrl = getString(R.string.alamatUrl);
        final String wisataUrl = alamatUrl + "api/wisata/" + id + "/video/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, wisataUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            WisataVideo wisataVideo;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listVideo = response.getJSONObject(i);
                                long id_video = listVideo.getLong("id");
                                long desa_wisata_id = listVideo.getLong("desa_wisata_id");
                                String video_wisata = listVideo.getString("file");

                                video_wisata = alamatUrl+video_wisata;

                                wisataVideo = new WisataVideo();
                                wisataVideo.setId_video(id_video);
                                wisataVideo.setDesa_wisata_id(desa_wisata_id);
                                wisataVideo.setVideo_wisata(video_wisata);

                                apiWisataVideo.add(wisataVideo);
                            }

                            adapterWisataVideo = new AdapterWisataVideo(ViewWisata.this, apiWisataVideo);
                            listVideo.setAdapter(adapterWisataVideo);
                            progressDialog.dismiss();

                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.e("JSON", e.toString());
                            Toast.makeText(ViewWisata.this, "Data terkendala gangguan teknis, tidak ditemukan atau Tidak terkoneksi", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Volley", error.toString());
                        Toast.makeText(ViewWisata.this, "Server tidak berfungsi atau Non-aktif", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(ViewWisata.this));
        requestQueue.add(jsonArrayRequest);
    }

    private void dataAtraksi() {
        final ProgressDialog progressDialog = new ProgressDialog(ViewWisata.this);
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        Intent wisata = getIntent();
        final long id_wisata = wisata.getLongExtra("id_wisata",0);
        String id = String.valueOf(id_wisata);

        final String alamatUrl = getString(R.string.alamatUrl);
        final String atraksiUrl = alamatUrl + "api/kegiatan/" + id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, atraksiUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            WisataKegiatan atraksi;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listAtraksi = response.getJSONObject(i);
                                long id_atraksi = listAtraksi.getLong("id");
                                long desa_wisata_id = listAtraksi.getLong("desa_wisata_id");
                                String nama_atraksi = listAtraksi.getString("nama_atraksi");
                                String deskripsi = listAtraksi.getString("deskripsi");
                                String foto_atraksi = listAtraksi.getString("foto");
                                String nama_wisata = listAtraksi.getString("nama_wisata");

                                foto_atraksi = alamatUrl+foto_atraksi;

                                atraksi = new WisataKegiatan();
                                atraksi.setId_atraksi(id_atraksi);
                                atraksi.setDesa_wisata_id(desa_wisata_id);
                                atraksi.setNama_atraksi(nama_atraksi);
                                atraksi.setDeskripsi(deskripsi);
                                atraksi.setFoto_atraksi(foto_atraksi);
                                atraksi.setNama_wisata(nama_wisata);

                                apiWisataKegiatan.add(atraksi);
                            }

                            adapterWisataKegiatan = new AdapterWisataKegiatan(getApplicationContext(), apiWisataKegiatan);
                            linearLayoutManager = new LinearLayoutManager(ViewWisata.this, LinearLayoutManager.VERTICAL, false);
                            customDecoration = new CustomDecoration(10);

                            listKegiatan.setAdapter(adapterWisataKegiatan);
                            adapterWisataKegiatan.notifyDataSetChanged();
                            adapterWisataKegiatan.notifyItemRemoved(response.length());
                            listKegiatan.setLayoutManager(linearLayoutManager);
                            progressDialog.dismiss();

                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.e("JSON", e.toString());
                            Toast.makeText(ViewWisata.this, "Data terkendala gangguan teknis, tidak ditemukan atau Tidak terkoneksi", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Volley", error.toString());
                        Toast.makeText(ViewWisata.this, "Server tidak berfungsi atau Non-aktif", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(ViewWisata.this));
        requestQueue.add(jsonArrayRequest);
    }
}
