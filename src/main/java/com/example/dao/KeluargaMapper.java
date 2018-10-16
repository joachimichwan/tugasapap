package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	
    @Select("select * from keluarga join kota join kecamatan join kelurahan "
    		+ "on keluarga.id_kelurahan = kelurahan.id and kelurahan.id_kecamatan = kecamatan.id and kecamatan.id_kota = kota.id "
    		+ "where keluarga.nomor_kk = #{nkk}")
    @Results(value = {
    		@Result(property="anggota", column="nomor_kk",
    			javaType = List.class,
    			many=@Many(select="selectAnggota_keluarga"))
    })
    KeluargaModel selectKeluarga (@Param("nkk") String nkk);

    @Select("select * from penduduk join keluarga join kota join kecamatan join kelurahan "
    		+ "on penduduk.id_keluarga = keluarga.id and keluarga.id_kelurahan = kelurahan.id "
    		+ "and kelurahan.id_kecamatan = kecamatan.id and kecamatan.id_kota = kota.id "
    		+ "where keluarga.nomor_kk = #{nkk}")
    List<PendudukModel> selectAnggota_keluarga (@Param("nkk")String nkk);
    
    @Insert("insert into keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) values (#{nomor_kk}, #{alamat}, "
    		+ "#{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void addKeluarga (KeluargaModel keluarga);

    @Update("UPDATE keluarga SET nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan} where id = #{id}")
    void updateKeluarga(KeluargaModel keluarga);

    @Update("UPDATE keluarga SET is_tidak_berlaku = 1")
    void updateBerlaku(@Param("nkk")String nkk);
    
}
