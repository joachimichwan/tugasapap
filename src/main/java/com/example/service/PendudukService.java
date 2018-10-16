package com.example.service;

import java.util.List;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

public interface PendudukService
{
    PendudukModel selectPenduduk (String nik);
	
    void updatePenduduk (PendudukModel penduduk);

    void addPenduduk (PendudukModel penduduk);
    
    KeluargaModel selectKeluargaPenduduk(String id_keluarga);

    void updateWafat(String nik);
    
    List<PendudukModel> selectAllPendudukKelurahan (String id_kelurahan);

}
