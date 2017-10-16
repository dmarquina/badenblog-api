package com.badenblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.badenblog.common.RestDataMessageResponse;
import com.badenblog.persistence.domain.Material;
import com.badenblog.repository.MaterialRepository;
import com.badenblog.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService{

	@Autowired
	private MaterialRepository materialRepository;

	public MaterialServiceImpl(MaterialRepository materialRepository) {
		this.materialRepository = materialRepository;
	}

	@Override
	public Page<Material> findAll(Pageable pageable) {
		return materialRepository.findAll(pageable);
	}

	@Override
	public Material findOne(int id) {
		return materialRepository.findOne(id);
	}

	@Override
	public RestDataMessageResponse create(Material material) {
		RestDataMessageResponse response;
		try {
			materialRepository.save(material);
			response = new RestDataMessageResponse("Creado satisfactoriamente.", true);
		} catch (Exception e) {
			response = new RestDataMessageResponse("Lo sentimos, a ocurrido un problema.", false);
		}
		return response;
	}

	@Override
	public RestDataMessageResponse update(Material material) {
		RestDataMessageResponse response;
		try {
			materialRepository.save(material);
			response = new RestDataMessageResponse("El material ha sido modificado exitosamente.", true);
		} catch (Exception e) {
			System.out.println("Hubo un problema en update user");
			response = new RestDataMessageResponse("La información no ha sido enviada correctamente.", false);
		}
		return response;
	}	
	
	@Override
	public RestDataMessageResponse delete(int id) {
		RestDataMessageResponse response;
		try {
			materialRepository.delete(id);
			response = new RestDataMessageResponse("El material ha sido eliminado exitosamente.", true);
		} catch (Exception e) {
			System.out.println("Error en MaterialService" + e.getMessage());
			response = new RestDataMessageResponse("No se ha logrado eliminar el user seleccionado.", false);
		}
		return response;
	}
}
