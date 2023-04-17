package com.maa.tp_eburtis.repository;

import com.maa.tp_eburtis.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartementRepository extends JpaRepository<Departement, String> {
}
