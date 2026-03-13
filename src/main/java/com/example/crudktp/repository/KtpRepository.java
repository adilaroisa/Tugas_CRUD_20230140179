package com.example.crudktp.repository;

import com.example.crudktp.model.entity.Ktp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KtpRepository extends JpaRepository<Ktp, Integer> {

    boolean existsByNomorKtp(String nomorKtp);

}