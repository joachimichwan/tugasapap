package com.example.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel
{
    private int id;
    private String nik;
    
    @NotNull
    @Size(min=1, max=128)
    private String nama;
    
    @NotNull
    private String tempat_lahir;

    @NotNull
    @Size(min=1, max=128)
    private String tanggal_lahir;
    
    @NotNull
    private String is_wni;
    
    @NotNull
    private String agama;
    
    @NotNull
    @Size(min=1, max=64)
    private String pekerjaan;
    
    @NotNull
    @Size(min=1, max=64)
    private String status_perkawinan;
    
    @NotNull
    @Size(min=1, max=64)
    private String status_dalam_keluarga;
    
    @NotNull
    private String golongan_darah;
    
    @NotNull
    private String id_keluarga;
    private String is_wafat;
    private String alamat;
    private String rt;
    private String rw;
	private String nama_kecamatan;
    private String nama_kota;
	private String nama_kelurahan;
	private String kewarganegaraan;
	private String status_kematian;
	private String jenis_kelamin;
	private String jk;
	private String tanggal;
	private String nomor_kk;
	
	public void setKewarganegaraan(String is_wni) {
		if(this.is_wni.equals("1")) {
			this.kewarganegaraan = "WNI";
		} else {
			this.kewarganegaraan = "WNA";
		}
	}
	
	public void setStatus_kematian(String is_wafat) {
		if(this.is_wafat.equals("1")) {
			this.status_kematian = "Meninggal";
		} else {
			this.status_kematian = "Hidup";
		}
	}
	
	public void setJk(String jenis_kelamin) {
		if(this.jenis_kelamin.equals("1")) {
			this.jk = "Wanita";
		} else {
			this.jk = "Pria";
		}
	}
	
	public void setTanggal(String tanggal_lahir) {
		String[] tanggalTemp = tanggal_lahir.split("-");
		this.tanggal = tanggalTemp[2] + "-" + tanggalTemp[1] + "-" + tanggalTemp[0];
	}
}
