package com.example.crudktp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "KTP")
public class Ktp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nomorKtp;

    private String namaLengkap;

    private String alamat;

    private LocalDate tanggalLahir;

    private String jenisKelamin;
}