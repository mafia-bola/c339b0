package com.disporapar.listdesawisata;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.disporapar.listdesawisata.API.Atraksi;
import com.disporapar.listdesawisata.CustomAdapter.AdapterAtraksi;
import com.disporapar.listdesawisata.CustomAdapter.CustomDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentAtraksi extends Fragment {

    RecyclerView recyclerView;
    AdapterAtraksi adapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Atraksi> apiAtraksi = new ArrayList<>();
    EditText editText;
    CustomDecoration customDecoration;
    ImageView btnSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atraksi, container, false);

        recyclerView = view.findViewById(R.id.listAtraksi);
        editText = view.findViewById(R.id.txtPencarian);
        dataAtraksi();
        apiAtraksi.clear();

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString() == null){
                    dataAtraksi();
                    clearView();
                } else {
                    dataAtraksi();
                    clearView();
                }
            }
        });

        return view;
    }

    private void clearView() {
        apiAtraksi.clear();
    }

    private void dataAtraksi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        final String pencarian = editText.getText().toString();
        final String alamatUrl = getString(R.string.alamatUrl);
        final String atraksiUrl = alamatUrl + "api/atraksi/" + pencarian;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, atraksiUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Atraksi atraksi;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listAtraksi = response.getJSONObject(i);
                                long id_atraksi = listAtraksi.getLong("id");
                                long desa_wisata_id = listAtraksi.getLong("desa_wisata_id");
                                String nama_atraksi = listAtraksi.getString("nama_atraksi");
                                String deskripsi = listAtraksi.getString("deskripsi");
                                String foto_atraksi = listAtraksi.getString("foto");
                                String nama_wisata = listAtraksi.getString("nama_wisata");

                                foto_atraksi = alamatUrl+foto_atraksi;

                                atraksi = new Atraksi();
                                atraksi.setId_atraksi(id_atraksi);
                                atraksi.setDesa_wisata_id(desa_wisata_id);
                                atraksi.setNama_atraksi(nama_atraksi);
                                atraksi.setDeskripsi(deskripsi);
                                atraksi.setFoto_atraksi(foto_atraksi);
                                atraksi.setNama_wisata(nama_wisata);

                                apiAtraksi.add(atraksi);
                            }

                            adapter = new AdapterAtraksi(getContext(), apiAtraksi);
                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            customDecoration = new CustomDecoration(10);

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
