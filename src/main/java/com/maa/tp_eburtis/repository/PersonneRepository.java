package com.maa.tp_eburtis.repository;

import com.maa.tp_eburtis.model.Personne;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonneRepository extends CrudRepository<Personne,Long> {
}
