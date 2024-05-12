package com.fastshop.net.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Products")
public class Product implements Serializable{
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@NotBlank(message = "Tên sản phẩm không được để trống.")
	@Column(name = "name", columnDefinition = "nvarchar(50)", nullable = false)
	String name;

	String image;
	
	@PositiveOrZero(message = "Giá sản phẩm phải lớn hơn 0.")
	Double price;

	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();

	@NotNull(message = "Bạn phải chọn trạng thái sản phẩm.")
	Boolean available;

	@PositiveOrZero(message = "Số lượng sản phẩm phải lớn hơn0.")
	Integer number;

	@ManyToOne
	@JoinColumn(name = "Categoryid")
	Category category;

	@Column(name = "describe", columnDefinition = "nvarchar(max)", nullable = true)
	String describe;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;	

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Comment> comments;
}
