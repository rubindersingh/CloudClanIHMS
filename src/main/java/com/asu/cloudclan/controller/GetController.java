package com.asu.cloudclan.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UriTemplate;

@RestController
public class GetController {

	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
	public void getImageAsByteArray(HttpServletResponse response) throws IOException {
				
	    InputStream in = new FileInputStream("E://Images//Cloud//signature2.jpg");
	    //System.out.println(in.read());
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    IOUtils.copy(in, response.getOutputStream());
	}
	
	/*@RequestMapping(value = "/images/{containerId}/**", method = RequestMethod.GET)
	public String getImage(HttpServletResponse response, @PathVariable("containerId")String containerId,HttpServletRequest request,
			@RequestParam(value="h", required=false)Integer height,@RequestParam(value="w", required=false)Integer width,@RequestParam(value="pw", required=false)Integer pad,@RequestParam(value="fl", required=false)String filter) throws IOException {
			String imagePath = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
			String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		    AntPathMatcher apm = new AntPathMatcher();
		    String finalPath = apm.extractPathWithinPattern(bestMatchPattern, imagePath);
		    System.out.println(containerId);	
			System.out.println(finalPath);	
			System.out.println(height);	
			
			System.out.println(width);	
			System.out.println(pad);	
			System.out.println(filter);	
			return "done";
	}*/
}
