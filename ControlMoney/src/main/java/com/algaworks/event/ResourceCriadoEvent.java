package com.algaworks.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public @Data class ResourceCriadoEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long codigo;

    public ResourceCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;
    }
}
