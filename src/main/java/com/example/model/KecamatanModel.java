package com.example.model;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KecamatanModel {
	private Integer id;
	private String kodeRuang;
	private String deskripsi;
	private int tipe;
	private double panjang;
	private double lebar;
	private double hargaSewa;
	private String lokasi;
	private String fasilitas;
	private int status;
}

