package com.pantasoft.jwtresource.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unsecured")
public class UnsecuredResource {

	@GetMapping
	public String unsecuredResource() {

		return "This is an unsecured resource";
	}
	
}
