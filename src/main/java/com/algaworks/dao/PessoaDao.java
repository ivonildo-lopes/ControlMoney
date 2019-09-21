package com.algaworks.dao;


import com.algaworks.model.Categoria;
import com.algaworks.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaDao extends JpaRepository<Pessoa, Long>  {


    @Query(value = "From Pessoa p where upper(p.nome) = upper(:nome)")
    List findByName(@Param("nome") String nome);

}