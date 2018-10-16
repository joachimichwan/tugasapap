package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
	@Autowired
    private PendudukMapper pendudukMapper;
	
    @Override
    public PendudukModel selectPenduduk (String nik)
    {
        log.info ("select penduduk with nik {}", nik);
        return pendudukMapper.selectPenduduk (nik);
    }

    public void updatePenduduk (PendudukModel penduduk)
	{
		log.info ("penduduk updated");
	  	pendudukMapper.updatePenduduk (penduduk);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info("penduduk added");
		pendudukMapper.addPenduduk (penduduk);
	}
	
	@Override
	public KeluargaModel selectKeluargaPenduduk(String id_keluarga) {
		log.info("informasi keluarga dengan id {} telah diterima", id_keluarga);
		return pendudukMapper.selectKeluargaPenduduk(id_keluarga);
	}

	@Override
	public void updateWafat(String nik) {
		pendudukMapper.updateWafat(nik);
	}
	
	@Override
	public List<PendudukModel> selectAllPendudukKelurahan(String id_kelurahan) {
		log.info("select penduduk dari kelurahan dengan id {}", id_kelurahan);
		return pendudukMapper.selectAllPendudukKelurahan(id_kelurahan);
	}
}
