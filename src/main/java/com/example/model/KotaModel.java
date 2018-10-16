package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KotaModel 
{
    private int id;
    private String kode_kota;
    private String nama_kota;
    private List<KecamatanModel> kecamatan;
}
