package com.example.service;

import java.util.List;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

public interface KeluargaService {
	
	KeluargaModel selectKeluarga(String nkk);
    
	void updateKeluarga (KeluargaModel keluarga);

    void addKeluarga (KeluargaModel keluarga);
    
    List<PendudukModel> selectAnggota_keluarga (String nik);
    
    void updateBerlaku(String nkk);

}
