package com.algaworks.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

    public static void main(String[] args) {
        BCryptPasswordEncoder cript = new BCryptPasswordEncoder();
        System.out.println(cript.encode("admin"));
    }
}
