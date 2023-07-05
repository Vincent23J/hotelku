package com.si61.hotelku.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.si61.hotelku.API.APIRequestData;
import com.si61.hotelku.API.RetroServer;
import com.si61.hotelku.Model.ModelResponse;
import com.si61.hotelku.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private EditText etNama, etAlamat, etUrlmap, etTelepon, etBintang, etDeskripsi;
    private Button btnSimpan;
    private String nama, alamat, urlmap, telepon, bintang, deskripsi;
    private String yId, yNama, yAlamat, yUrlmap, yTelepon, yBintang, yDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etUrlmap = findViewById(R.id.et_urlmap);
        etTelepon = findViewById(R.id.et_telepon);
        etBintang = findViewById(R.id.et_bintang);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        btnSimpan = findViewById(R.id.btn_simpan);

        Intent tangkap = getIntent();

        yId = tangkap.getStringExtra("xId");
        yNama = tangkap.getStringExtra("xNama");
        yAlamat = tangkap.getStringExtra("xAlamat");
        yUrlmap = tangkap.getStringExtra("xUrlmap");
        yTelepon = tangkap.getStringExtra("xTelepon");
        yBintang = tangkap.getStringExtra("xBintang");
        yDeskripsi = tangkap.getStringExtra("xDeskripsi");

        etNama.setText(yNama);
        etAlamat.setText(yAlamat);
        etUrlmap.setText(yUrlmap);
        etTelepon.setText(yTelepon);
        etBintang.setText(yBintang);
        etDeskripsi.setText(yDeskripsi);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                alamat = etAlamat.getText().toString();
                urlmap = etUrlmap.getText().toString();
                telepon = etTelepon.getText().toString();
                bintang = etBintang.getText().toString();
                deskripsi = etDeskripsi.getText().toString();

                if (nama.trim().isEmpty()){
                    etNama.setError("Nama hotel harus diisi");
                }
                else if(alamat.trim().isEmpty()){
                    etAlamat.setError("Alamat hotel harus diisi");
                }
                else if(urlmap.trim().isEmpty()){
                    etUrlmap.setError("Urlmap hotel harus diisi");
                }
                else if(telepon.trim().isEmpty()){
                    etTelepon.setError("Telepon hotel harus diisi");
                }
                else if(bintang.trim().isEmpty()){
                    etBintang.setError("Bintang hotel harus diisi");
                }
                else if(deskripsi.trim().isEmpty()){
                    etDeskripsi.setError("Deskripsi hotel harus diisi");
                }
                else{
                    prosesUbah();
                }
            }
        });

    }

    private void prosesUbah(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, nama, alamat, urlmap, telepon, bintang, deskripsi);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}