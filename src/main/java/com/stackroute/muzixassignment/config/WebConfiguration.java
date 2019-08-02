package com.stackroute.muzixassignment.config;

import com.stackroute.muzixassignment.service.TrackService;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class WebConfiguration {

    TrackService trackService;


    public WebConfiguration(TrackService trackService) {
        this.trackService = trackService;
    }


//    @Bean
//    ServletRegistrationBean h2servletRegistration()
//    {
//        ServletRegistrationBean registrationBean=new ServletRegistrationBean(new WebServlet());
//        registrationBean.addUrlMappings("/console/*");
//        return registrationBean;
//    }
}

