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
import com.disporapar.listdesawisata.API.Wisata;
import com.disporapar.listdesawisata.CustomAdapter.AdapterWisata;
import com.disporapar.listdesawisata.CustomAdapter.CustomDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentWisata extends Fragment {

    RecyclerView recyclerView;
    AdapterWisata adapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Wisata> apiWisata = new ArrayList<>();
    EditText editText;
    CustomDecoration customDecoration;
    ImageView btnSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wisata, container, false);

        recyclerView = view.findViewById(R.id.listWisata);
        editText = view.findViewById(R.id.txtPencarian);
        dataWisata();
        apiWisata.clear();

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("")){
                    dataWisata();
                    clearView();
                } else {
                    dataWisata();
                    clearView();
                }
            }
        });

        return view;
    }

    private void clearView() {
        apiWisata.clear();
    }

    private void dataWisata() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        final String pencarian = editText.getText().toString();
        final String alamatUrl = getString(R.string.alamatUrl);
        final String wisataUrl = alamatUrl + "api/wisata/" + pencarian;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, wisataUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Wisata wisata;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listWisata = response.getJSONObject(i);
                                long id_wisata = listWisata.getLong("id");
                                String nama_wisata = listWisata.getString("nama_wisata");
                                String alamat_wisata = listWisata.getString("alamat_wisata");
                                String sejarah_desa = listWisata.getString("sejarah_desa");
                                String demografi = listWisata.getString("demografi");
                                String potensi = listWisata.getString("potensi");
                                String thumbnail = listWisata.getString("thumbnail");
                                double lat = listWisata.getDouble("lat");
                                double lng = listWisata.getDouble("lng");

                                thumbnail = alamatUrl+thumbnail;

                                wisata = new Wisata();
                                wisata.setId_wisata(id_wisata);
                                wisata.setNama_wisata(nama_wisata);
                                wisata.setAlamat_wisata(alamat_wisata);
                                wisata.setSejarah_desa(sejarah_desa);
                                wisata.setDemografi(demografi);
                                wisata.setPotensi(potensi);
                                wisata.setThumbnail(thumbnail);
                                wisata.setLat(lat);
                                wisata.setLng(lng);

                                apiWisata.add(wisata);
                            }

                            adapter = new AdapterWisata(getContext(), apiWisata);
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
