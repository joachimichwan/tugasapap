package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService{

    @Autowired
    private KeluargaMapper keluargaMapper;

	public KeluargaModel selectKeluarga(String nkk) {
		log.info ("select keluarga with nkk {}", nkk);
        return keluargaMapper.selectKeluarga (nkk);
	}
	  
	@Override
	public void updateKeluarga (KeluargaModel keluarga)
	  {
	  	log.info ("keluarga updated");
	  	keluargaMapper.updateKeluarga (keluarga);
	  }
	
	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		log.info("keluarga added");
		keluargaMapper.addKeluarga(keluarga);
	}

	@Override
	public List<PendudukModel> selectAnggota_keluarga(String nik) {
		log.info("anggota keluarga selected");
		return keluargaMapper.selectAnggota_keluarga(nik);
	}

	@Override
	public void updateBerlaku(String nkk) {
		keluargaMapper.updateBerlaku(nkk);	
	}

}
