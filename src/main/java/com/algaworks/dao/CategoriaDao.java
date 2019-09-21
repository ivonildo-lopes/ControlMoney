package com.algaworks.dao;


import com.algaworks.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaDao extends JpaRepository<Categoria, Long>  {


    @Query(value = "From Categoria c where upper(c.nome) = upper(:nome)")
    List findByName(@Param("nome") String nome);

}