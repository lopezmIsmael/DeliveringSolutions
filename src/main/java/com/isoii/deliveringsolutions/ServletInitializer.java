package com.isoii.deliveringsolutions;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Controller
public class ServletInitializer extends SpringBootServletInitializer{

    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DeliveringSolutions.class);
	}
}
