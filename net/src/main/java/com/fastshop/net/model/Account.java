package com.fastshop.net.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "Accounts")
public class Account  implements Serializable{
	@Id
	@NotBlank(message = "(*) Tên đăng nhập không được để trống.")
	@Column(name = "username", columnDefinition = "varchar(30)", nullable = false)
	private String username;

	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+=.,/\\`~*-]{6,}$", message = "(*) Mật khẩu không được nhỏ hơn 6 ký tự.")
	@Column(name = "password", columnDefinition = "nvarchar(15)", nullable = false)
	private String password;

	@NotBlank(message = "(*) Tên đầy đủ không được để trống")
	@Column(name = "fullname", columnDefinition = "nvarchar(30)", nullable = false)
	private String fullname;

	@Email(message = "(*) Email không hợp lệ.")
	@NotBlank(message = "(*) Email không được để trống")
	@Pattern(regexp = "^[a-zA-Z0-9._-]{1,}+@[a-zA-Z0-9-]{3,}+\\.[a-zA-Z0-9-]{1,}$", message = "(*) Email phải đúng định dạng example@example.com")
	@Column(name = "email", columnDefinition = "varchar(30)", nullable = false, unique = true)
	private String email;

	@Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", message = "(*) Số điện thoại không đúng định dạng.")
	@Column(name = "phone", columnDefinition = "varchar(15)", nullable = false, unique = true)
	private String phone;

	Boolean active;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Comment> comments;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<History> histories;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)  
	// FetchType.EAGER cho phép được tải về dữ liệu khi dữ liệu mẹ được tải xong FetchType.LAZY ko cần tải cho những trường ko cần thiết
	List<Authority> authorities;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<ATM> atms;
}
