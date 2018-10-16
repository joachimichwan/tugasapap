package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KecamatanMapper;
import com.example.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService{
	
	@Autowired
    private KecamatanMapper kecamatanMapper;
	
	@Override
	public List<KecamatanModel> selectAllKecamatan(int id_kota) {
		log.info ("select all kecamatan dengan id kota {}", id_kota);
		return kecamatanMapper.selectAllKecamatan(id_kota);
	}

	@Override
	public KecamatanModel selectKecamatan(int id) {
		log.info ("select kecamatan dengan id {}", id);
		return kecamatanMapper.selectKecamatan(id);
	}

	@Override
	public List<KecamatanModel> selectAll() {
		log.info ("select kecamatan");
		return kecamatanMapper.selectAll();
	}

}
