package com.disporapar.listdesawisata;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.disporapar.listdesawisata.API.Kategori;
import com.disporapar.listdesawisata.API.Kegiatan;
import com.disporapar.listdesawisata.CustomAdapter.AdapterKategori;
import com.disporapar.listdesawisata.CustomAdapter.AdapterKegiatan;
import com.disporapar.listdesawisata.CustomAdapter.CustomDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentKegiatan extends Fragment {

    RecyclerView recyclerView;
    AdapterKegiatan adapter;
    AdapterKategori adapterKategori;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Kegiatan> apiKegiatan = new ArrayList<>();
    ArrayList<Kategori> apiKategori = new ArrayList<>();
    CustomDecoration customDecoration;
    AppCompatSpinner spinner;
    TextView txtNamaKategori;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kegiatan, container, false);

        recyclerView = view.findViewById(R.id.listKegiatan);
        spinner = view.findViewById(R.id.spinKategori);
        txtNamaKategori = view.findViewById(R.id.txtNamaKategori);
        txtNamaKategori.setText("");

        dataKategori();
        dataKegiatan();

        return view;
    }

    private void dataKategori() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        final String alamatUrl = getString(R.string.alamatUrl);
        final String kategoriUrl = alamatUrl + "api/kategori/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, kategoriUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Kategori kategori;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listKegiatan = response.getJSONObject(i);
                                long id = listKegiatan.getLong("id");
                                String nama_kategori = listKegiatan.getString("nama_kategori");

                                kategori = new Kategori();
                                kategori.setId_kategori(id);
                                kategori.setNama_kategori(nama_kategori);

                                apiKategori.add(kategori);
                            }

                            adapterKategori = new AdapterKategori(getContext(), apiKategori);
                            spinner.setAdapter(adapterKategori);

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    txtNamaKategori.setText(apiKategori.get(position).getNama_kategori());
                                    apiKegiatan.clear();
                                    dataKegiatan();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    txtNamaKategori.setText("");
                                    apiKegiatan.clear();
                                    dataKegiatan();
                                }
                            });

                            progressDialog.dismiss();

                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.e("JSON", e.toString());
                            Toast.makeText(getContext(), "Data terkendala gangguan teknis, tidak ditemukan atau Tidak terkoneksi", Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getContext(), "Server tidak berfungsi atau Non-aktif", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()).getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void dataKegiatan() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        final String pencarian = txtNamaKategori.getText().toString();
        final String alamatUrl = getString(R.string.alamatUrl);
        final String atraksiUrl = alamatUrl + "api/kegiatan/" + pencarian;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, atraksiUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Kegiatan kegiatan;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listKegiatan = response.getJSONObject(i);
                                long id_atraksi = listKegiatan.getLong("id");
                                long desa_wisata_id = listKegiatan.getLong("desa_wisata_id");
                                long kategori_id = listKegiatan.getLong("kategori_id");
                                String nama_kegiatan = listKegiatan.getString("nama_kegiatan");
                                String deskripsi = listKegiatan.getString("deskripsi");
                                String foto_kegiatan = listKegiatan.getString("foto");
                                String nama_kategori = listKegiatan.getString("nama_kategori");

                                foto_kegiatan = alamatUrl+foto_kegiatan;

                                kegiatan = new Kegiatan();
                                kegiatan.setId_atraksi(id_atraksi);
                                kegiatan.setDesa_wisata_id(desa_wisata_id);
                                kegiatan.setKategori_id(kategori_id);
                                kegiatan.setNama_kegiatan(nama_kegiatan);
                                kegiatan.setDeskripsi(deskripsi);
                                kegiatan.setFoto_kegiatan(foto_kegiatan);
                                kegiatan.setNama_kategori(nama_kategori);

                                apiKegiatan.add(kegiatan);
                            }

                            adapter = new AdapterKegiatan(getContext(), apiKegiatan);
                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            adapter.notifyItemRemoved(response.length());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            progressDialog.dismiss();

                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.e("JSON", e.toString());
                            Toast.makeText(getContext(), "Data terkendala gangguan teknis, tidak ditemukan atau Tidak terkoneksi", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getContext(), "Server tidak berfungsi atau Non-aktif", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()).getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }
}
