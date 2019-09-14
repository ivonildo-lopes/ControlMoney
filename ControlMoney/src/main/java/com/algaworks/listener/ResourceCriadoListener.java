package com.algaworks.listener;

import com.algaworks.event.ResourceCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ResourceCriadoListener implements ApplicationListener<ResourceCriadoEvent> {

    @Override
    public void onApplicationEvent(ResourceCriadoEvent resourceCriadoEvent) {
        HttpServletResponse response = resourceCriadoEvent.getResponse();
        Long codigo = resourceCriadoEvent.getCodigo();

        adicionarHeaderLocation(response, codigo);

    }

    private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toString());
        response.setStatus(HttpStatus.CREATED.value());
    }
}
