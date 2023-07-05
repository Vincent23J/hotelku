package com.si61.hotelku.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.si61.hotelku.API.APIRequestData;
import com.si61.hotelku.API.RetroServer;
import com.si61.hotelku.Adapter.AdapterHotel;
import com.si61.hotelku.Model.ModelHotel;
import com.si61.hotelku.Model.ModelResponse;
import com.si61.hotelku.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvHotel;
    private ProgressBar pbHotel;
    private FloatingActionButton fabTambah;
    private RecyclerView.Adapter adHotel;
    private RecyclerView.LayoutManager lmHotel;
    private List<ModelHotel> listHotel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvHotel = findViewById(R.id.rv_hotel);
        pbHotel = findViewById(R.id.pb_hotel);
        fabTambah = findViewById(R.id.fab_tambah);

        lmHotel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvHotel.setLayoutManager(lmHotel);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    public void retrieveHotel(){
        pbHotel.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listHotel = response.body().getData();

                adHotel = new AdapterHotel(MainActivity.this, listHotel);
                rvHotel.setAdapter(adHotel);
                adHotel.notifyDataSetChanged();
                pbHotel.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
                pbHotel.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveHotel();
    }
}