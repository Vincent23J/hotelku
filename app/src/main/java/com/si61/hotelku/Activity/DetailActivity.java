package com.si61.hotelku.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.si61.hotelku.R;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama1, tvAlamat1, tvUrlmap1, tvTelepon1, tvBintang1, tvDeskripsi1;
    private String yId1, yNama1, yAlamat1, yUrlmap1, yTelepon1, yBintang1, yDeskripsi1;
    private RatingBar rtbRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama1 = findViewById(R.id.tv_nama1);
        tvAlamat1 = findViewById(R.id.tv_alamat1);
        tvUrlmap1 = findViewById(R.id.tv_utlmap1);
        tvTelepon1 = findViewById(R.id.tv_telepon1);
        tvBintang1 = findViewById(R.id.tv_bintang1);
        tvDeskripsi1 = findViewById(R.id.tv_deskripsi1);
        rtbRating = findViewById(R.id.rtb_rating);

        Intent tangkap = getIntent();

        yId1 = tangkap.getStringExtra("xId1");
        yNama1 = tangkap.getStringExtra("xNama1");
        yAlamat1 = tangkap.getStringExtra("xAlamat1");
        yUrlmap1 = tangkap.getStringExtra("xUrlmap1");
        yTelepon1 = tangkap.getStringExtra("xTelepon1");
        yBintang1 = tangkap.getStringExtra("xBintang1");
        yDeskripsi1 = tangkap.getStringExtra("xDeskripsi1");


        tvNama1.setText(yNama1);
        tvAlamat1.setText(yAlamat1);
        tvUrlmap1.setText(yUrlmap1);
        tvTelepon1.setText("Telp : " + yTelepon1);
        tvBintang1.setText("Bintang : " + yBintang1);
        tvDeskripsi1.setText(yDeskripsi1);
        rtbRating.setRating(Integer.parseInt(yBintang1));
    }
}