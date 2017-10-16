package com.badenblog.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.badenblog.common.RestDataMessageResponse;
import com.badenblog.common.constants.Constants;
import com.badenblog.persistence.domain.Userprofile;
import com.badenblog.repository.UserprofileRepository;
import com.badenblog.security.jwt.JwtUserFactory;
import com.badenblog.security.utils.PasswordUtils;
import com.badenblog.service.UserprofileService;

@Service
public class UserprofileServiceImpl implements UserprofileService, UserDetailsService {

	@Autowired
	private UserprofileRepository userRepository;

	public UserprofileServiceImpl(UserprofileRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Page<Userprofile> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Userprofile findOne(int id) {
		return userRepository.findOne(id);
	}

	@Override
	public Userprofile findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public RestDataMessageResponse create(Userprofile user) {
		RestDataMessageResponse response;
		try {
			if(userRepository.findByEmail(user.getEmail()) == null){
				if(user.getPassword()!=null){
					String pwdUser = user.getPassword();
					String claveCodificada = PasswordUtils.encodePassword(pwdUser);
					user.setPassword(claveCodificada);
				}
				if(user.getGender()!=null){
					user.setGender(translateGender(user.getGender()));
				}
				user.setActive(Constants.ACTIVE_STATE);
				user.setAdmin(Constants.INACTIVE_STATE);
				user.setDateInsert(new Date());
				user.setLastPasswordResetDate(new Date());
				userRepository.save(user);
				response = new RestDataMessageResponse("Te has registrado exitosamente.", true);
			}else{
				response = new RestDataMessageResponse("Lo sentimos, este email ya se encuentra registrado.", false);
			}
		} catch (Exception e) {
			response = new RestDataMessageResponse("Lo sentimos, a ocurrido un problema.", false);
		}
		return response;
	}

	@Override
	public RestDataMessageResponse update(Userprofile user) {
		RestDataMessageResponse response;
		try {
			userRepository.save(user);
			response = new RestDataMessageResponse("El user ha sido modificado exitosamente.", true);
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
			userRepository.delete(id);
			response = new RestDataMessageResponse("El user ha sido eliminado exitosamente.", true);
		} catch (Exception e) {
			System.out.println("Error en UserprofileService" + e.getMessage());
			response = new RestDataMessageResponse("No se ha logrado eliminar el user seleccionado.", false);
		}
		return response;
	}
	/**
	 * Método para validar existencia de user, según número de documento de identidad. (No busca por userName, se tuvo que dejar con tal nombre el servicio porque la interfaz que llama al método es propio de Spring Security y debe llevar el mismo nombre de servicio)
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Userprofile user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("user no encontrado con nro. de documento '%s'.", email));
        } else {
            return JwtUserFactory.create(user);
        }
	}
	
	public String translateGender(String gender){
		if(gender.equalsIgnoreCase("male")){
			return "masculino";
		}
		else if(gender.equalsIgnoreCase("female")){
			return "femenino";
		}else{
			return null;
		}
	}
}

