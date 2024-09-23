package com.IsoII.DeliveringSolutions;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Controller
public class ServletInitializer extends SpringBootServletInitializer{

    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DeliveringSolutions.class);
	}
}
