package com.easyfrutas.email;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


	@Data @AllArgsConstructor @NoArgsConstructor
	public class Mail {

	    private String from;
	    private String to;
	    private String subject;
	    private String nombreCompleto;
	    private String codigo;
	    private List<Object> attachments;
	    private Map<String, Object> model;

}
