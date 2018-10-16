package com.example.service;

import java.util.List;

import com.example.model.KelurahanModel;

public interface KelurahanService {
	
	KelurahanModel selectKelurahan (int id);

	List<KelurahanModel> selectAllKelurahan(int id_kecamatan);
	
	List<KelurahanModel> selectAll();
}
