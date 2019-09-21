package com.algaworks.dao;


import com.algaworks.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Long>  {

    @Query(value = "From Usuario p where upper(p.nome) = upper(:nome)")
    List findByName(@Param("nome") String nome);

    @Query(value = "From Usuario u WHERE UPPER(u.email) = UPPER(:email)")
    Optional<Usuario> findByEmail(@Param("email") String email);

}