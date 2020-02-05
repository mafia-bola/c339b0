package com.disporapar.listdesawisata;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.disporapar.listdesawisata.API.Event;
import com.disporapar.listdesawisata.CustomAdapter.AdapterEvent;
import com.disporapar.listdesawisata.CustomAdapter.CustomDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentEvent extends Fragment {

    RecyclerView recyclerView;
    AdapterEvent adapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Event> apiEvent = new ArrayList<>();
    CustomDecoration customDecoration;
    AppCompatSpinner spinner;
    private String[] spinStatus = {
            "Sudah Berjalan",
            "Sedang Berjalan",
            "Akan Berjalan"
    };
    TextView txtStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        recyclerView = view.findViewById(R.id.listEvent);
        txtStatus = view.findViewById(R.id.txtStatus);
        spinner = view.findViewById(R.id.spinStatus);

        final ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, spinStatus);

        spinner.setAdapter(adapterStatus);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtStatus.setText(adapterStatus.getItem(position));
                dataEvent();
                apiEvent.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void dataEvent() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Proses Pengambilan Data");
        progressDialog.setMessage("mohon ditunggu .....");
        progressDialog.show();

        final String pencarian = txtStatus.getText().toString();
        final String alamatUrl = getString(R.string.alamatUrl);
        final String eventUrl = alamatUrl + "api/event/" + pencarian;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, eventUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Event event;

                            for (int i=0; i<response.length(); i++){
                                JSONObject listEvent = response.getJSONObject(i);
                                long id_event = listEvent.getLong("id");
                                String judul_event = listEvent.getString("judul");
                                String deskripsi = listEvent.getString("deskripsi");
                                String foto_event = listEvent.getString("foto");
                                String status = listEvent.getString("status");

                                foto_event = alamatUrl+foto_event;

                                event = new Event();
                                event.setId_event(id_event);
                                event.setJudul_event(judul_event);
                                event.setDeskripsi(deskripsi);
                                event.setFoto_event(foto_event);
                                event.setStatus(status);

                                apiEvent.add(event);
                            }

                            adapter = new AdapterEvent(getContext(), apiEvent);
                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

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
