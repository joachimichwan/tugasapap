package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.model.KotaModel;

@Mapper
public interface KotaMapper {
	
	@Select("select * from kota")
	List<KotaModel> selectAllKota();
	
	@Select("SELECT * FROM kota where kota.id = #{id}")
	KotaModel selectKota(int id);

}
