package com.badenblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.badenblog.persistence.domain.Userprofile;

@Repository
public interface UserprofileRepository
		extends JpaRepository<Userprofile, Integer>, PagingAndSortingRepository<Userprofile, Integer> {

	public Userprofile findByEmail(String email);

}
