package com.fastshop.net.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "status")
public class Status implements Serializable{
    @Id
    Integer id;

    @Column(name = "kind", columnDefinition = "nvarchar(50)", nullable = false)
    String kind;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    List<Order> orders;
}
