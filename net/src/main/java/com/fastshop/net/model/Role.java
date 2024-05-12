package com.fastshop.net.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "roles")
public class Role implements Serializable{
	@Id
	private String id;
	private String name;
	private String level;
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	List<Authority> authorities;
}