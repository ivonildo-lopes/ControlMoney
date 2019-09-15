package com.algaworks.dao;


import com.algaworks.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoDao extends JpaRepository<Lancamento, Long>  {

}