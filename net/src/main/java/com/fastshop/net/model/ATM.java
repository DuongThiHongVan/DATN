package com.fastshop.net.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@Entity
@Table(name = "ATM")
public class ATM implements Serializable {
    @Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(name = "name", columnDefinition = "varchar(150)", nullable = false)
    String name;

    @Column(name = "company", columnDefinition = "varchar(70)", nullable = false)
    String company;

	@Column(name = "number", columnDefinition = "varchar(30)", nullable = false, unique = true)
    private String number;

    @ManyToOne @JoinColumn(name = "username", nullable = false)
	private Account account;

    Boolean valid;
}
