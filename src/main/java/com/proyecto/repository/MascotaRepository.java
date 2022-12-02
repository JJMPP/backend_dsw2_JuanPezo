package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.entity.Mascot;

@Repository
public interface MascotaRepository extends JpaRepository<Mascot, Integer>{

}
