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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comments")
public class Comment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

    @Column(name = "rate", columnDefinition = "int")
    @Min(0)
    @Max(5)
    Integer rate;

    @Column(name = "feedback", columnDefinition = "nvarchar(250)")
    String feedback;

    @Temporal(TemporalType.DATE)
    @Column(name = "datePost")
    Date datePost = new Date();

    @ManyToOne
	@JoinColumn(name = "Productid")
    Product product;

    @ManyToOne
    @JoinColumn(name = "Username")
    Account account;
}
