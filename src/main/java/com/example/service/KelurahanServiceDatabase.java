package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KelurahanMapper;
import com.example.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService{

	@Autowired
    private KelurahanMapper kelurahanMapper;
	
	public KelurahanModel selectKelurahan(int id) {
		log.info ("select kelurahan dengan id {}", id);
        return kelurahanMapper.selectKelurahan (id);
	}

	@Override
	public List<KelurahanModel> selectAllKelurahan(int id_kecamatan) {
		log.info ("select all kelurahan dengan id kecamatan {}", id_kecamatan);
		return kelurahanMapper.selectAllKelurahan(id_kecamatan);
	}

	@Override
	public List<KelurahanModel> selectAll() {
		log.info ("select all kelurahan");
		return kelurahanMapper.selectAll();
	}
}
