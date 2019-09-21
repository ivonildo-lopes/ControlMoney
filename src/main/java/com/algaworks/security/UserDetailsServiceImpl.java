package com.algaworks.security;

import com.algaworks.model.Usuario;
import com.algaworks.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /**
         * spring é o reponsavel por fazer a autenticação
         */
        Usuario usuario = this.service.findByEmail(email);
//        return new User(email, usuario.getSenha(), getPermissoes(usuario));
        return new UsuarioSistema(usuario,getPermissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        usuario.getPermissoes().forEach(permissao ->
                authorities.add(new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase())));

        return authorities;
    }
}
