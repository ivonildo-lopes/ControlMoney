package com.algaworks.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin("*")
public class SwaggerResource {

    @Value("${spring.profiles.active}")
    private String profile;

    public static final String DEV = "dev";

    @RequestMapping(value = "/documetation", method = RequestMethod.GET)
    public String redirect() {
        return DEV.equals(profile)? "redirect:swagger-ui.html": "";
    }

}
