package com.algaworks.config.token;

import java.util.HashMap;
import java.util.Map;

import com.algaworks.security.UsuarioSistema;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;


public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();

        Map<String, Object> addInfo = new HashMap<>();
        addInfo.put("usuarioLogado", usuarioSistema.getUsuario().getNome());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
        return accessToken;
    }

}