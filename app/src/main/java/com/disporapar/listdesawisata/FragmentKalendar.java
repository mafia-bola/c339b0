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
import com.disporapar.listdesawisata.API.Kalendar;
import com.disporapar.listdesawisata.CustomAdapter.AdapterKalendar;
import com.disporapar.listdesawisata.CustomAdapter.CustomDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentKalendar extends Fragment {

    RecyclerView recyclerView;
    AdapterKalendar adapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Kalendar> apiKalendar = new ArrayList<>();
    EditText editText;
    CustomDecoration customDecoration;
    ImageView btnSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kalender, container, false);

        recyclerView = view.findViewById(R.id.listKalendar);
        editText = view.findViewById(R.id.txtPencarian);
        dataKalendar();
        apiKalendar.clear();

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString() == null){
                    dataKalendar();
                    clearView();
                } else {
                    dataKalendar();
                    clearView();
                }
            }
        });

        return view;
    }

    private void clearView() {
        apiKalendar.clear();
    }

    private void dataKalendar() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        final String pencarian = editText.getText().toString();
        final String alamatUrl = getString(R.string.alamatUrl);
        final String kalendarUrl = alamatUrl + "api/kalendar/" + pencarian;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, kalendarUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Kalendar kalendar;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listKalendar = response.getJSONObject(i);
                                long id_kalendar = listKalendar.getLong("id");
                                String judul_kalender = listKalendar.getString("judul");
                                String deskripsi = listKalendar.getString("deskripsi");
                                String foto_kalender = listKalendar.getString("foto");

                                foto_kalender = alamatUrl+foto_kalender;

                                kalendar = new Kalendar();
                                kalendar.setId_kalendar(id_kalendar);
                                kalendar.setJudul_kalendar(judul_kalender);
                                kalendar.setDeskripsi(deskripsi);
                                kalendar.setFoto_kalendar(foto_kalender);

                                apiKalendar.add(kalendar);
                            }

                            adapter = new AdapterKalendar(getContext(), apiKalendar);
                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            customDecoration = new CustomDecoration(10);

                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            adapter.notifyItemRemoved(response.length());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
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
