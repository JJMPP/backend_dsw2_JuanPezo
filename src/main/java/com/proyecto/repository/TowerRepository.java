package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.entity.Tower;

@Repository
public interface TowerRepository extends JpaRepository<Tower ,Integer> {

}
