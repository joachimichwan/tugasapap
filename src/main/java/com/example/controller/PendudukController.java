package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

@Controller
public class PendudukController
{
    @Autowired
    PendudukService pendudukDAO;
    
    @Autowired
    KeluargaService keluargaDAO;
    
    @Autowired
    KotaService kotaDAO;
    
    @Autowired
    KecamatanService kecamatanDAO;
    
    @Autowired
    KelurahanService kelurahanDAO;

    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/penduduk/tambah")
    public String addPenduduk ()
    {
        return "forms/form-add-penduduk";
    }


    @RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
    public String addPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk)
    {	
    	KeluargaModel infoPenduduk = pendudukDAO.selectKeluargaPenduduk(penduduk.getId_keluarga());
    	String[] tanggalTemp = penduduk.getTanggal_lahir().split("-");
    	
    	if(penduduk.getJenis_kelamin().equals("0")) {
    		tanggalTemp[2] = Integer.parseInt(tanggalTemp[2]) + 40 + "";
    	}
    	
    	String NIK = infoPenduduk.getKode_kecamatan().substring(0,6) + tanggalTemp[2] + tanggalTemp[1] + tanggalTemp[0].substring(2, 4) + "0001";
    	Long nik_baru = Long.parseLong(NIK);
    	
    	while(true) {
    		PendudukModel pendudukCheck = pendudukDAO.selectPenduduk (nik_baru + "");
    		if(pendudukCheck != null) {
    			nik_baru++;
    		} else {
    			break;
    		}
    	}
    	
    	penduduk.setNik(nik_baru + "");
    	
    	pendudukDAO.addPenduduk (penduduk);
    	model.addAttribute ("nik", penduduk.getNik());
        return "success/success-add-penduduk";    		
    }

    @RequestMapping(value = "/penduduk", method = RequestMethod.GET)
    public String viewPenduduk (Model model,
            @RequestParam(value = "nik", required = false) String nik)
    {
    	PendudukModel penduduk = pendudukDAO.selectPenduduk (nik);
    	String tanggal = "";
    	
        if (penduduk != null) {
        	
        	penduduk.setKewarganegaraan(penduduk.getIs_wni());
        	penduduk.setStatus_kematian(penduduk.getIs_wafat());
        	String[] tanggalTemp = penduduk.getTanggal_lahir().split("-");
    		
    		if(tanggalTemp[1].equals("01")) {
    			tanggalTemp[1] = "Januari";
    		} else if(tanggalTemp[1].equals("02")) {
    			tanggalTemp[1] = "Februari";
    		} else if(tanggalTemp[1].equals("03")) {
    			tanggalTemp[1] = "Maret";
    		} else if(tanggalTemp[1].equals("04")) {
    			tanggalTemp[1] = "April";
    		} else if(tanggalTemp[1].equals("05")) {
    			tanggalTemp[1] = "Mei";
    		} else if(tanggalTemp[1].equals("06")) {
    			tanggalTemp[1] = "Juni";
    		} else if(tanggalTemp[1].equals("07")) {
    			tanggalTemp[1] = "Juli";
    		} else if(tanggalTemp[1].equals("08")) {
    			tanggalTemp[1] = "Agustus";
    		} else if(tanggalTemp[1].equals("09")) {
    			tanggalTemp[1] = "September";
    		} else if(tanggalTemp[1].equals("10")) {
    			tanggalTemp[1] = "Oktober";
    		} else if(tanggalTemp[1].equals("11")) {
    			tanggalTemp[1] = "November";
    		} else {
    			tanggalTemp[1] = "Desember";
    		}
    		
    		tanggal = tanggalTemp[2] + " " + tanggalTemp[1] + " " + tanggalTemp[0];
    		model.addAttribute ("tanggal", tanggal);
            
    		model.addAttribute ("penduduk", penduduk);
            return "views/view-penduduk";
        } else {
            model.addAttribute ("nik", nik);
            return "error/not-found-penduduk";
        }
    }
    
    @RequestMapping("/penduduk/mati")
    public String pendudukWafat (Model model, @RequestParam(value = "nik") String nik)
    {
    	String tanggal = "";
    	
    	if(pendudukDAO.selectPenduduk(nik) != null) {
        	PendudukModel penduduk = pendudukDAO.selectPenduduk (nik);
        	List<PendudukModel> anggotaKeluarga = keluargaDAO.selectAnggota_keluarga(penduduk.getNomor_kk());
        	
        	if(penduduk != null) {
        		pendudukDAO.updateWafat(penduduk.getNik());
        		
        		int count = 0;
        		for(int i = 0; i < anggotaKeluarga.size(); i++) {
        			if(anggotaKeluarga.get(i).getIs_wafat().equals("1")) {
        				count++;
        			}
        		}
        		if(count == anggotaKeluarga.size() - 1) {
        			keluargaDAO.updateBerlaku(penduduk.getNomor_kk());
        		}
        		
        		penduduk.setKewarganegaraan(penduduk.getIs_wni());
            	penduduk.setStatus_kematian(penduduk.getIs_wafat());
            	String[] tanggalTemp = penduduk.getTanggal_lahir().split("-");
        		
        		if(tanggalTemp[1].equals("01")) {
        			tanggalTemp[1] = "Januari";
        		} else if(tanggalTemp[1].equals("02")) {
        			tanggalTemp[1] = "Februari";
        		} else if(tanggalTemp[1].equals("03")) {
        			tanggalTemp[1] = "Maret";
        		} else if(tanggalTemp[1].equals("04")) {
        			tanggalTemp[1] = "April";
        		} else if(tanggalTemp[1].equals("05")) {
        			tanggalTemp[1] = "Mei";
        		} else if(tanggalTemp[1].equals("06")) {
        			tanggalTemp[1] = "Juni";
        		} else if(tanggalTemp[1].equals("07")) {
        			tanggalTemp[1] = "Juli";
        		} else if(tanggalTemp[1].equals("08")) {
        			tanggalTemp[1] = "Agustus";
        		} else if(tanggalTemp[1].equals("09")) {
        			tanggalTemp[1] = "September";
        		} else if(tanggalTemp[1].equals("10")) {
        			tanggalTemp[1] = "Oktober";
        		} else if(tanggalTemp[1].equals("11")) {
        			tanggalTemp[1] = "November";
        		} else {
        			tanggalTemp[1] = "Desember";
        		}
        		
        		tanggal = tanggalTemp[2] + " " + tanggalTemp[1] + " " + tanggalTemp[0];
        		model.addAttribute ("tanggal", tanggal);
                
        		
        		model.addAttribute ("penduduk", penduduk);
                return "views/view-penduduk";
        	}
        }
        model.addAttribute ("nik", nik);
        return "error/not-found-penduduk";
    }
    
    @RequestMapping("/penduduk/ubah/{nik}")
    public String updatePenduduk (Model model, @PathVariable(value = "nik") String nik)
    {
        if(pendudukDAO.selectPenduduk(nik) != null) {
        	PendudukModel penduduk = pendudukDAO.selectPenduduk (nik);
        	
        	if(penduduk != null) {
        		model.addAttribute ("penduduk", penduduk);
        		return "forms/form-update-penduduk";
        	}
        }
        model.addAttribute ("nik", nik);
        return "error/not-found-penduduk";
    }
    
    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String updatePendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk)
    {	
    	String nik_lama = penduduk.getNik();
    	String tanggalTemp[] = penduduk.getTanggal_lahir().split("-");
    	String nik_tanggal = tanggalTemp[2] +  tanggalTemp[1] +  tanggalTemp[0].substring(2, 4);
    	KeluargaModel infoPenduduk = pendudukDAO.selectKeluargaPenduduk(penduduk.getId_keluarga());
    	
    	if(!nik_lama.substring(6, 12).equals(nik_tanggal)) {
    		nik_tanggal = (infoPenduduk.getKode_kecamatan().substring(0,6) + nik_tanggal + "0001");
    	} else {
    		nik_tanggal = nik_lama;
    	}
    	
    	Long nik_baru = Long.parseLong(nik_tanggal);
    	
    	while(true) {
    		PendudukModel pendudukCheck = pendudukDAO.selectPenduduk (nik_baru + "");
    		if(pendudukCheck != null) {
    			nik_baru++;
    		} else {
    			break;
    		}
    	}

    	penduduk.setNik(nik_baru + "");
    	pendudukDAO.updatePenduduk (penduduk);
    	
    	model.addAttribute ("nik_lama", nik_lama);
        return "success/success-update-penduduk";    		
    }
    
    @RequestMapping("/penduduk/cari")
    public String cariPenduduk (Model model,
            @RequestParam(value = "kt", required = false) String kota,
            @RequestParam(value = "kc", required = false) String kecamatan,
            @RequestParam(value = "kl", required = false) String kelurahan)
    {	
    	if(kota == null) {
	    	List<KotaModel> listKota = kotaDAO.selectAllKota();
	    	model.addAttribute ("kota", listKota);
	        return "views/cariPendudukKt";
    	} else {
    		if(kecamatan == null) {
    			KotaModel kotaObj = kotaDAO.selectKota(Integer.parseInt(kota));
    			List<KecamatanModel> listKecamatan = kecamatanDAO.selectAllKecamatan(Integer.parseInt(kota));
    	    	
    			System.out.println(kotaObj.toString());
    			
    	    	model.addAttribute ("kota", kotaObj);
    	    	model.addAttribute ("kecamatan", listKecamatan);
    	        return "views/cariPendudukKc";
        	} else {
        		if(kelurahan == null) {
        			KotaModel kotaObj = kotaDAO.selectKota(Integer.parseInt(kota));
        			KecamatanModel kecamatanObj = kecamatanDAO.selectKecamatan(Integer.parseInt(kecamatan));
        			List<KelurahanModel> listKelurahan = kelurahanDAO.selectAllKelurahan(Integer.parseInt(kecamatan));
        	    	
        	    	model.addAttribute ("kota", kotaObj);
        	    	model.addAttribute ("kecamatan", kecamatanObj);
        	    	model.addAttribute ("kelurahan", listKelurahan);
        	        return "views/cariPendudukKl";
            	} 
        	}
    	}
    	KotaModel kotaObj = kotaDAO.selectKota(Integer.parseInt(kota));
		KecamatanModel kecamatanObj = kecamatanDAO.selectKecamatan(Integer.parseInt(kecamatan));
    	KelurahanModel kelurahanObj = kelurahanDAO.selectKelurahan(Integer.parseInt(kelurahan));
		
    	List<PendudukModel> pendudukKelurahan = pendudukDAO.selectAllPendudukKelurahan(kelurahan);
    	
    	for(int i = 0; i < pendudukKelurahan.size(); i++) {
    		pendudukKelurahan.get(i).setJk(pendudukKelurahan.get(i).getJenis_kelamin());
    	}
    	
    	PendudukModel pendudukTermuda = pendudukKelurahan.get(0);
    	PendudukModel pendudukTertua = pendudukKelurahan.get(0);
    	
    	for(int i = 0; i < pendudukKelurahan.size(); i++) {
    		if(pendudukTermuda.getTanggal_lahir().compareTo(pendudukKelurahan.get(i).getTanggal_lahir()) > 0) {
    			pendudukTermuda = pendudukKelurahan.get(i);
    		}
    		if(pendudukTertua.getTanggal_lahir().compareTo(pendudukKelurahan.get(i).getTanggal_lahir()) <= 0) {
    			pendudukTertua = pendudukKelurahan.get(i);
    		}
    	}
    	
    	model.addAttribute ("pendudukTertua", pendudukTertua);
    	model.addAttribute ("pendudukTermuda", pendudukTermuda);
    	model.addAttribute ("kota", kotaObj);
    	model.addAttribute ("kecamatan", kecamatanObj);
    	model.addAttribute ("kelurahan", kelurahanObj);
    	model.addAttribute ("penduduk", pendudukKelurahan);
    	return "views/hasilPencarian";
    }
}
