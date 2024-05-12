package com.fastshop.net.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Addresses")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "place", columnDefinition = "nvarchar(200)", nullable = false)
    String place;

    Boolean choose;

    @Column(name = "username", columnDefinition = "varchar(15)", nullable = false)
    String username;
}