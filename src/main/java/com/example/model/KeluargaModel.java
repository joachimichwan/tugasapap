package com.example.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel 
{
	private int id;
    private String nomor_kk;
    
    @NotNull
    @Size(min=1, max=256)
    private String alamat;
    
    @NotNull
    @Size(min=1, max=3)
    private String rt;
    
    @NotNull
    @Size(min=1, max=3)
    private String rw;
    
    private int is_tidak_berlaku;
    private List<PendudukModel> anggota;
    private String id_kecamatan;
    private String nama_kecamatan;
	private String id_kota;
    private String nama_kota;
    private String id_kelurahan;
	private String nama_kelurahan;
	private String kode_kota;
    private String kode_kecamatan;
    private String status;
    
	public void setStatus(int is_tidak_berlaku) {
		if(this.is_tidak_berlaku == 0) {
			this.status = "Tidak Berlaku";
		} else {
			this.status = "Berlaku";
		}
	}
}
