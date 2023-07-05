package com.si61.hotelku.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelHotel> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelHotel> getData() {
        return data;
    }
}
