package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	
	@Select("select * from kecamatan where kecamatan.id_kota = #{id_kota}")
	List<KecamatanModel> selectAllKecamatan(int id_kota);
	
	@Select("SELECT * FROM kecamatan where kecamatan.id = #{id}")
	KecamatanModel selectKecamatan(int id);
	
	@Select("select * from kecamatan")
	List<KecamatanModel> selectAll();
}
