package com.firma.firma.repository;

import com.firma.firma.entity.Firma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FirmaRepository extends JpaRepository <Firma, Long>{
}
