package com.fastshop.net.model;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", columnDefinition = "nvarchar(150)", nullable = false)
    String title;

    @Column(name = "fileName", columnDefinition = "nvarchar(200)", nullable = true)
    String fileName;

    @Temporal(TemporalType.DATE)
    @Column(name = "sentDate")
    Date sentDate = new Date();

    @Column(name = "status")
    Boolean status;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    Account account;
}
