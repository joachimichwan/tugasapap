package com.example.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

@Controller
public class KeluargaController {
	
	@Autowired
    PendudukService pendudukDAO;
	
	@Autowired
    KeluargaService keluargaDAO;
	
	@Autowired
    KelurahanService kelurahanDAO;
	
	@Autowired
    KecamatanService kecamatanDAO;
	
	@Autowired
    KotaService kotaDAO;
	
	@RequestMapping("/keluarga/tambah")
    public String addKeluarga (Model model)
    {
		model.addAttribute ("kota", kotaDAO.selectAllKota());
		model.addAttribute ("kecamatan", kecamatanDAO.selectAll());
		model.addAttribute ("kelurahan", kelurahanDAO.selectAll());
        return "forms/form-add-keluarga";
    }


    @RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
    public String addKeluargaSubmit ( Model model, @Valid @ModelAttribute KeluargaModel keluarga, BindingResult result)
    {	
    	if(result.hasErrors()) {
    		return "error/form-error";
    	}
    	
    	LocalDate date = LocalDate.now();
    	String localdate = DateTimeFormatter.ofPattern("ddMMyy").format(date);
    	
    	KelurahanModel kelurahan1 = kelurahanDAO.selectKelurahan(Integer.parseInt(keluarga.getId_kelurahan()));
    	
    	String nkk = kelurahan1.getKode_kelurahan().substring(0, 6) + localdate + "0001";
    	Long nkk_baru = Long.parseLong(nkk);
    	    	
    	while(true) {
    		KeluargaModel keluargaCheck = keluargaDAO.selectKeluarga (nkk_baru + "");
    		if(keluargaCheck != null) {
    			nkk_baru++;
    		} else {
    			break;
    		}
    	}
    	
    	keluarga.setNomor_kk(nkk_baru + "");
    	
    	keluargaDAO.addKeluarga(keluarga);
    	model.addAttribute ("nkk", keluarga.getNomor_kk());
        return "success/success-add-keluarga";    		
    }
    
    @RequestMapping(value = "/keluarga", method = RequestMethod.GET)
    public String viewKeluarga (Model model,
            @RequestParam(value = "nkk", required = false) String nkk)
    {
    	KeluargaModel keluarga = keluargaDAO.selectKeluarga (nkk);
    	
        if (keluarga != null) {            
    		for(int i = 0; i < keluarga.getAnggota().size(); i++) {
    			keluarga.getAnggota().get(i).setKewarganegaraan(keluarga.getAnggota().get(i).getIs_wni());
    			keluarga.getAnggota().get(i).setTanggal(keluarga.getAnggota().get(i).getTanggal_lahir());
    			keluarga.getAnggota().get(i).setJk(keluarga.getAnggota().get(i).getJenis_kelamin());
    		}
    		keluarga.setStatus(keluarga.getIs_tidak_berlaku());
        	model.addAttribute ("keluarga", keluarga);
        	model.addAttribute ("nkk", nkk);
            return "views/view-keluarga";
        } else {
            model.addAttribute ("nkk", nkk);
            return "error/not-found-keluarga";
        }
    }

    @RequestMapping("/keluarga/ubah/{nkk}")
    public String updateKeluarga (Model model, @PathVariable(value = "nkk") String nkk)
    {
        if(keluargaDAO.selectKeluarga(nkk) != null) {
        	KeluargaModel keluarga = keluargaDAO.selectKeluarga (nkk);
        	
        	if(keluarga != null) {
        		keluarga.setNomor_kk(nkk);
        		model.addAttribute ("id", keluarga.getId());
        		model.addAttribute ("keluarga", keluarga);
        		model.addAttribute ("kota", kotaDAO.selectAllKota());
        		model.addAttribute ("kecamatan", kecamatanDAO.selectAll());
        		model.addAttribute ("kelurahan", kelurahanDAO.selectAll());
        		return "forms/form-update-keluarga";
        	}
        }
        model.addAttribute ("nkk", nkk);
        return "error/not-found-keluarga";
    }
    
    @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String updateKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga)
    {	
    	LocalDate date = LocalDate.now();
    	String localdate = DateTimeFormatter.ofPattern("ddMMyy").format(date);
    	String nkk_lama = keluarga.getNomor_kk();
    	String[] kelurahan = keluarga.getId_kelurahan().split(",");
    	KelurahanModel kelurahan1 = kelurahanDAO.selectKelurahan(Integer.parseInt(kelurahan[1]));   	
    	String nkk_baru = kelurahan1.getKode_kelurahan().substring(0, 6) + localdate;
    	
    	keluarga.setId_kelurahan(kelurahan[1]);
    	
    	KeluargaModel keluargaLama = keluargaDAO.selectKeluarga (keluarga.getNomor_kk());
    	System.out.println(keluargaLama.getAnggota());

    	if(!nkk_lama.substring(0, 12).equals(nkk_baru)) {
    		for(int i = 0; i < keluargaLama.getAnggota().size(); i++) {
        		String nik_lama = keluargaLama.getAnggota().get(i).getNik();
            	Long nik_baru = Long.parseLong(nkk_baru.substring(0, 6) + nik_lama.substring(6, 12) + "0001");
            	
            	while(true) {
            		PendudukModel pendudukCheck = pendudukDAO.selectPenduduk (nik_baru + "");
            		if(pendudukCheck != null) {
            			nik_baru++;
            		} else {
            			break;
            		}
            	}
            	
            	System.out.println(nik_baru);
        		keluargaLama.getAnggota().get(i).setNik(nik_baru + "");
        		
        		pendudukDAO.updatePenduduk (keluargaLama.getAnggota().get(i));
        		System.out.println(keluargaLama.getAnggota().get(i).getNik());
        	}
    	}
    	
    	Long nkk = Long.parseLong(nkk_baru + "0001");
    	
    	while(true) {
    		KeluargaModel keluargaCheck = keluargaDAO.selectKeluarga (nkk + "");
    		if(keluargaCheck != null) {
    			nkk++;
    		} else {
    			break;
    		}
    	}
    	
    	keluarga.setNomor_kk(nkk + "");
    	
    	System.out.println(keluarga.toString());
    	
    	keluargaDAO.updateKeluarga (keluarga);
    	model.addAttribute ("nkk", nkk_lama);
        return "success/success-update-keluarga";    		
    }
}
