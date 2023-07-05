package com.si61.hotelku.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.si61.hotelku.API.APIRequestData;
import com.si61.hotelku.API.RetroServer;
import com.si61.hotelku.Activity.DetailActivity;
import com.si61.hotelku.Activity.MainActivity;
import com.si61.hotelku.Activity.TambahActivity;
import com.si61.hotelku.Activity.UbahActivity;
import com.si61.hotelku.Model.ModelHotel;
import com.si61.hotelku.Model.ModelResponse;
import com.si61.hotelku.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterHotel extends RecyclerView.Adapter<AdapterHotel.VHHotel>{
    private Context ctx;
    private List<ModelHotel> listhotel;

    public AdapterHotel(Context ctx, List<ModelHotel> listhotel) {
        this.ctx = ctx;
        this.listhotel = listhotel;
    }

    @NonNull
    @Override
    public VHHotel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item_hotel, parent, false);
        return new VHHotel(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHHotel holder, int position) {
        ModelHotel MH = listhotel.get(position);
        holder.tvId.setText(MH.getId());
        holder.tvNama.setText(MH.getNama());
        holder.tvAlamat.setText(MH.getAlamat());
        holder.tvUrlmap.setText(MH.getUrlmap());
        holder.tvTelepon.setText(MH.getTelepon());
        holder.tvBintang.setText(MH.getBintang());
        holder.tvDeskripsi.setText(MH.getDeskripsi());



    }

    @Override
    public int getItemCount() {
        return listhotel.size();
    }

    public class VHHotel extends RecyclerView.ViewHolder{
        TextView tvId, tvNama, tvAlamat, tvUrlmap, tvTelepon, tvBintang, tvDeskripsi;

        public VHHotel(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvUrlmap = itemView.findViewById(R.id.tv_urlmap);
            tvTelepon = itemView.findViewById(R.id.tv_telepon);
            tvBintang = itemView.findViewById(R.id.tv_bintang);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, DetailActivity.class);
                    kirim.putExtra("xId1", tvId.getText().toString());
                    kirim.putExtra("xNama1", tvNama.getText().toString());
                    kirim.putExtra("xAlamat1", tvAlamat.getText().toString());
                    kirim.putExtra("xUrlmap1", tvUrlmap.getText().toString());
                    kirim.putExtra("xTelepon1", tvTelepon.getText().toString());
                    kirim.putExtra("xBintang1", tvBintang.getText().toString());
                    kirim.putExtra("xDeskripsi1", tvDeskripsi.getText().toString());
                    ctx.startActivity(kirim);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda memilih "+ tvNama.getText().toString() +", Operasi apa yang akan dilakukan?");

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", tvId.getText().toString());
                            kirim.putExtra("xNama", tvNama.getText().toString());
                            kirim.putExtra("xAlamat", tvAlamat.getText().toString());
                            kirim.putExtra("xUrlmap", tvUrlmap.getText().toString());
                            kirim.putExtra("xTelepon", tvTelepon.getText().toString());
                            kirim.putExtra("xBintang", tvBintang.getText().toString());
                            kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                            ctx.startActivity(kirim);
                        }
                    });

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prosesHapus(tvId.getText().toString());
                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }

        void prosesHapus(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieveHotel();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
