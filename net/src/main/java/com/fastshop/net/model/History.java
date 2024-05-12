package com.fastshop.net.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Histories")
public class History implements Serializable{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", columnDefinition = "nvarchar(250)", nullable = false)
    String title;

    @Column(name = "link", columnDefinition = "varchar(250)", nullable = true)
    String link;

    @Column(name = "schedual", columnDefinition = "datetime", nullable = false)
    Date schedual = new Date();

    Boolean status;

    @ManyToOne
    @JoinColumn(name = "Username", nullable = false)
    Account account;
}
