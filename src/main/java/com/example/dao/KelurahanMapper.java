package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	
	@Select("SELECT * FROM kelurahan where kelurahan.id = #{id}")
	KelurahanModel selectKelurahan(int id);
	
	@Select("select * from kelurahan where kelurahan.id_kecamatan = #{id}")
	List<KelurahanModel> selectAllKelurahan(int id_kecamatan);

	@Select("select * from kelurahan")
	List<KelurahanModel> selectAll();
}
