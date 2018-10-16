package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.PendudukModel;
import com.example.model.KeluargaModel;

@Mapper
public interface PendudukMapper
{
    @Select("select * from penduduk join keluarga join kota join kecamatan join kelurahan "
    		+ "on penduduk.id_keluarga = keluarga.id and keluarga.id_kelurahan = kelurahan.id "
    		+ "and kelurahan.id_kecamatan = kecamatan.id and kecamatan.id_kota = kota.id "
    		+ "where nik = #{nik}")
    PendudukModel selectPenduduk (@Param("nik") String nik);
        
    @Select("select distinct * from keluarga join kota join kecamatan join kelurahan "
    		+ "on keluarga.id_kelurahan = kelurahan.id and kelurahan.id_kecamatan = kecamatan.id "
    		+ "and kecamatan.id_kota = kota.id  where keluarga.id = #{id_keluarga}")
    KeluargaModel selectKeluargaPenduduk (@Param("id_keluarga") String id_keluarga);
    
    @Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, "
    		+ "pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) values (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, "
    		+ "#{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void addPenduduk (PendudukModel penduduk);
    
    @Update("UPDATE penduduk SET nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, "
    		+ "tanggal_lahir = #{tanggal_lahir}, golongan_darah = #{golongan_darah}, agama = #{agama}, status_perkawinan = #{status_perkawinan}, "
    		+ "pekerjaan = #{pekerjaan}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga} WHERE id = #{id}")
    void updatePenduduk(PendudukModel penduduk);
    
    @Update("UPDATE penduduk SET is_wafat = 1 where nik = #{nik}")
    void updateWafat(@Param("nik") String nik);
    
    @Select("select * from penduduk join keluarga on penduduk.id_keluarga = keluarga.id where keluarga.id_kelurahan = #{id_kelurahan}")
    List<PendudukModel> selectAllPendudukKelurahan (@Param("id_kelurahan") String id_kelurahan);

}
