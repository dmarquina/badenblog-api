package com.badenblog.persistence.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.badenblog.persistence.enums.AuthorityName;

@Entity
@Table(name = "authority")
public class Authority {
	
    private Long id;

    private AuthorityName name;

    private List<Userprofile> userprofile;

    @Id
	@Column(name = "id_authority", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }
    
    @ManyToMany(mappedBy = "authorities")
    public List<Userprofile> getUserprofiles() {
        return userprofile;
    }

    public void setUserprofiles(List<Userprofile> userprofile) {
        this.userprofile = userprofile;
    }
}