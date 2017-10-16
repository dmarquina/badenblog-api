package com.badenblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.badenblog.common.RestDataMessageResponse;
import com.badenblog.persistence.domain.Userprofile;

public interface UserprofileService {
	RestDataMessageResponse create(Userprofile user);

	RestDataMessageResponse update(Userprofile user);

	RestDataMessageResponse delete(int id);

	Page<Userprofile> findAll(Pageable pageable);

	Userprofile findOne(int id);

	Userprofile findByEmail(String email);
}
